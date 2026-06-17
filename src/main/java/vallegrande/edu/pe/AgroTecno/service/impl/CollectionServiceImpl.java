package vallegrande.edu.pe.AgroTecno.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import vallegrande.edu.pe.AgroTecno.dto.CollectionDetailRequest;
import vallegrande.edu.pe.AgroTecno.dto.CollectionDetailResponse;
import vallegrande.edu.pe.AgroTecno.dto.CollectionRequest;
import vallegrande.edu.pe.AgroTecno.dto.CollectionResponse;
import vallegrande.edu.pe.AgroTecno.model.Collection;
import vallegrande.edu.pe.AgroTecno.model.CollectionDetail;
import vallegrande.edu.pe.AgroTecno.repository.CollectionDetailRepository;
import vallegrande.edu.pe.AgroTecno.repository.CollectionRepository;
import vallegrande.edu.pe.AgroTecno.service.CollectionService;

@Service
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionDetailRepository collectionDetailRepository;

    public CollectionServiceImpl(CollectionRepository collectionRepository,
            CollectionDetailRepository collectionDetailRepository) {
        this.collectionRepository = collectionRepository;
        this.collectionDetailRepository = collectionDetailRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CollectionResponse> findAll() {
        return collectionRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CollectionResponse findById(Integer id) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Collection no encontrada con ID: " + id));

        return toResponse(collection);
    }

    @Override
    @Transactional
    public CollectionResponse save(CollectionRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }

        Integer installmentsCount = request.getInstallmentsCount() == null ? 1 : request.getInstallmentsCount();
        if (installmentsCount < 1) {
            throw new IllegalArgumentException("installmentsCount debe ser mayor o igual a 1");
        }

        String paymentStatus = request.getPaymentStatus() == null || request.getPaymentStatus().isBlank()
                ? "P"
                : request.getPaymentStatus().trim().toUpperCase();

        if (paymentStatus.length() != 1) {
            throw new IllegalArgumentException("paymentStatus debe tener un único carácter");
        }

        List<CollectionDetailRequest> detailRequests = request.getDetails() == null
                ? new ArrayList<>()
                : request.getDetails();

        BigDecimal totalAmount = resolveTotalAmount(request.getTotalAmount(), detailRequests);

        Collection collection = new Collection();
        collection.setTotalAmount(totalAmount);
        collection.setInstallmentsCount(installmentsCount);
        collection.setPaymentStatus(paymentStatus);
        collection.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        collection.setOrderOrderId(request.getOrderOrderId());

        Collection savedCollection = collectionRepository.save(collection);

        if (!detailRequests.isEmpty()) {
            List<CollectionDetail> details = new ArrayList<>();
            for (CollectionDetailRequest detailRequest : detailRequests) {
                BigDecimal quantity = detailRequest.getQuantity();
                BigDecimal unitPrice = detailRequest.getUnitPrice();
                BigDecimal subtotal = quantity.multiply(unitPrice).setScale(8, RoundingMode.HALF_UP);

                CollectionDetail detail = new CollectionDetail();
                detail.setCollection(savedCollection);
                detail.setLineNumber(detailRequest.getLineNumber());
                detail.setDescription(detailRequest.getDescription());
                detail.setQuantity(quantity);
                detail.setUnitPrice(unitPrice);
                detail.setSubtotal(subtotal);
                details.add(detail);
            }
            collectionDetailRepository.saveAll(details);
            savedCollection.setDetails(details);
        }

        return toResponse(savedCollection);
    }

    private BigDecimal resolveTotalAmount(BigDecimal requestedTotalAmount, List<CollectionDetailRequest> detailRequests) {
        if (detailRequests == null || detailRequests.isEmpty()) {
            if (requestedTotalAmount == null) {
                throw new IllegalArgumentException("totalAmount es obligatorio cuando no se envían detalles");
            }
            if (requestedTotalAmount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("totalAmount debe ser mayor que cero");
            }
            return requestedTotalAmount.setScale(8, RoundingMode.HALF_UP);
        }

        BigDecimal computedTotal = detailRequests.stream()
                .map(detail -> detail.getQuantity().multiply(detail.getUnitPrice()).setScale(8, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(8, RoundingMode.HALF_UP);

        return computedTotal;
    }

    private CollectionResponse toResponse(Collection collection) {
        List<CollectionDetailResponse> detailResponses = collection.getDetails() == null
                ? List.of()
                : collection.getDetails().stream()
                        .map(detail -> new CollectionDetailResponse(
                                detail.getCollectionDetailId(),
                                detail.getLineNumber(),
                                detail.getDescription(),
                                detail.getQuantity(),
                                detail.getUnitPrice(),
                                detail.getSubtotal()))
                        .toList();

        return new CollectionResponse(
                collection.getCollectionId(),
                collection.getTotalAmount(),
                collection.getInstallmentsCount(),
                collection.getPaymentStatus(),
                collection.getRegistrationDate(),
                collection.getOrderOrderId(),
                detailResponses);
    }
}
