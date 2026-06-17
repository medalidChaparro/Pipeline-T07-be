package vallegrande.edu.pe.AgroTecno.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import vallegrande.edu.pe.AgroTecno.dto.InstallmentRequest;
import vallegrande.edu.pe.AgroTecno.dto.InstallmentResponse;
import vallegrande.edu.pe.AgroTecno.model.Collection;
import vallegrande.edu.pe.AgroTecno.model.Installment;
import vallegrande.edu.pe.AgroTecno.repository.CollectionRepository;
import vallegrande.edu.pe.AgroTecno.repository.InstallmentRepository;
import vallegrande.edu.pe.AgroTecno.service.InstallmentService;

@Service
public class InstallmentServiceImpl implements InstallmentService {

    private final InstallmentRepository installmentRepository;
    private final CollectionRepository collectionRepository;

    public InstallmentServiceImpl(InstallmentRepository installmentRepository,
            CollectionRepository collectionRepository) {
        this.installmentRepository = installmentRepository;
        this.collectionRepository = collectionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstallmentResponse> findAll() {
        return installmentRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstallmentResponse> findByCollectionId(Integer collectionId) {
        return installmentRepository.findByCollectionCollectionId(collectionId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InstallmentResponse findById(Integer id) {
        Installment installment = installmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Installment no encontrada con ID: " + id));
        return toResponse(installment);
    }

    @Override
    @Transactional
    public InstallmentResponse save(InstallmentRequest request) {
        validateRequest(request);

        Collection collection = collectionRepository.findById(request.getCollectionCollectionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Collection no encontrada con ID: " + request.getCollectionCollectionId()));

        Installment installment = new Installment();
        installment.setAmountPaid(request.getAmountPaid());
        installment.setPaymentType(request.getPaymentType().trim());
        installment.setPaymentDate(parsePaymentDate(request.getPaymentDate()));
        installment.setObservation(request.getObservation());
        installment.setCollection(collection);
        installment.setDeletedAt(null);

        Installment saved = installmentRepository.save(installment);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public InstallmentResponse update(Integer id, InstallmentRequest request) {
        validateRequest(request);

        Installment existing = installmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Installment no encontrada con ID: " + id));

        Collection collection = collectionRepository.findById(request.getCollectionCollectionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Collection no encontrada con ID: " + request.getCollectionCollectionId()));

        existing.setAmountPaid(request.getAmountPaid());
        existing.setPaymentType(request.getPaymentType().trim());
        existing.setPaymentDate(parsePaymentDate(request.getPaymentDate()));
        existing.setObservation(request.getObservation());
        existing.setCollection(collection);

        return toResponse(installmentRepository.save(existing));
    }

    @Override
    @Transactional
    public InstallmentResponse delete(Integer id) {
        Installment installment = installmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Installment no encontrada con ID: " + id));

        installment.setDeletedAt(LocalDateTime.now(ZoneId.of("UTC")));
        return toResponse(installmentRepository.save(installment));
    }

    @Override
    @Transactional
    public InstallmentResponse restore(Integer id) {
        Installment installment = installmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Installment no encontrada con ID: " + id));

        installment.setDeletedAt(null);
        return toResponse(installmentRepository.save(installment));
    }

    private void validateRequest(InstallmentRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }
        if (request.getAmountPaid() == null || request.getAmountPaid().signum() <= 0) {
            throw new IllegalArgumentException("amountPaid debe ser mayor que cero");
        }
        if (request.getPaymentType() == null || request.getPaymentType().isBlank()) {
            throw new IllegalArgumentException("paymentType es obligatorio");
        }
        if (request.getPaymentDate() == null || request.getPaymentDate().isBlank()) {
            throw new IllegalArgumentException("paymentDate es obligatorio");
        }
        if (request.getCollectionCollectionId() == null) {
            throw new IllegalArgumentException("collectionCollectionId es obligatorio");
        }
    }

    private InstallmentResponse toResponse(Installment installment) {
        return new InstallmentResponse(
                installment.getInstallmentId(),
                installment.getAmountPaid(),
                installment.getPaymentType(),
                installment.getPaymentDate() == null ? null : installment.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                installment.getObservation(),
                installment.getCollection() == null ? null : installment.getCollection().getCollectionId(),
                installment.getDeletedAt() == null ? null : installment.getDeletedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    private LocalDateTime parsePaymentDate(String paymentDate) {
        try {
            return LocalDateTime.parse(paymentDate.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception ex) {
            throw new IllegalArgumentException("paymentDate debe tener el formato yyyy-MM-dd HH:mm:ss", ex);
        }
    }
}
