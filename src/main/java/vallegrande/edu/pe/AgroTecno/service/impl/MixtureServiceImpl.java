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

import vallegrande.edu.pe.AgroTecno.dto.MixtureDetailRequest;
import vallegrande.edu.pe.AgroTecno.dto.MixtureDetailResponse;
import vallegrande.edu.pe.AgroTecno.dto.MixtureRequest;
import vallegrande.edu.pe.AgroTecno.dto.MixtureResponse;
import vallegrande.edu.pe.AgroTecno.model.Formula;
import vallegrande.edu.pe.AgroTecno.model.Insumo;
import vallegrande.edu.pe.AgroTecno.model.Mixture;
import vallegrande.edu.pe.AgroTecno.model.MixtureDetail;
import vallegrande.edu.pe.AgroTecno.repository.FormulaRepository;
import vallegrande.edu.pe.AgroTecno.repository.InsumoRepository;
import vallegrande.edu.pe.AgroTecno.repository.MixtureDetailRepository;
import vallegrande.edu.pe.AgroTecno.repository.MixtureRepository;
import vallegrande.edu.pe.AgroTecno.service.MixtureService;

@Service
public class MixtureServiceImpl implements MixtureService {

    private final MixtureRepository mixtureRepository;
    private final MixtureDetailRepository mixtureDetailRepository;
    private final FormulaRepository formulaRepository;
    private final InsumoRepository insumoRepository;

    public MixtureServiceImpl(MixtureRepository mixtureRepository,
            MixtureDetailRepository mixtureDetailRepository,
            FormulaRepository formulaRepository,
            InsumoRepository insumoRepository) {
        this.mixtureRepository = mixtureRepository;
        this.mixtureDetailRepository = mixtureDetailRepository;
        this.formulaRepository = formulaRepository;
        this.insumoRepository = insumoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MixtureResponse> findAll() {
        return mixtureRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MixtureResponse findById(Integer id) {
        Mixture mixture = mixtureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Mixture no encontrada con ID: " + id));

        return toResponse(mixture);
    }

    @Override
    @Transactional
    public MixtureResponse save(MixtureRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }

        // Validar que la fórmula existe
        if (request.getFormulaId() == null || request.getFormulaId() <= 0) {
            throw new IllegalArgumentException("formulaId es obligatorio y debe ser mayor que cero");
        }

        Formula formula = formulaRepository.findById(request.getFormulaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Fórmula no encontrada con ID: " + request.getFormulaId()));

        // Validar cantidad por defecto
        if (request.getDefaultQuantity() == null || request.getDefaultQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("defaultQuantity debe ser mayor que cero");
        }

        // Resolver porcentaje
        BigDecimal percentageQuantity = request.getPercentageQuantity() == null 
                ? BigDecimal.ZERO 
                : request.getPercentageQuantity();

        if (percentageQuantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("percentageQuantity no puede ser negativo");
        }

        // Resolver estado
        String status = request.getStatus() == null || request.getStatus().isBlank()
                ? "A"
                : request.getStatus().trim().toUpperCase();

        if (status.length() != 1) {
            throw new IllegalArgumentException("status debe tener un único carácter");
        }

        // Obtener detalles de la solicitud
        List<MixtureDetailRequest> detailRequests = request.getDetails() == null
                ? new ArrayList<>()
                : request.getDetails();

        // Resolver costo total
        BigDecimal totalCost = resolveTotalCost(request.getTotalCost(), detailRequests);

        // Crear la mezcla
        Mixture mixture = new Mixture();
        mixture.setFormula(formula);
        mixture.setDefaultQuantity(request.getDefaultQuantity());
        mixture.setPercentageQuantity(percentageQuantity);
        mixture.setProductionTime(request.getProductionTime());
        mixture.setStatus(status);
        mixture.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        mixture.setTotalCost(totalCost);

        Mixture savedMixture = mixtureRepository.save(mixture);

        // Guardar detalles si existen
        if (!detailRequests.isEmpty()) {
            List<MixtureDetail> details = new ArrayList<>();
            
            for (MixtureDetailRequest detailRequest : detailRequests) {
                // Validar que el suministro/insumo existe
                if (detailRequest.getSupplyId() == null || detailRequest.getSupplyId() <= 0) {
                    throw new IllegalArgumentException("supplyId es obligatorio en los detalles");
                }

                Insumo supply = insumoRepository.findById(detailRequest.getSupplyId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Suministro/Insumo no encontrado con ID: " + detailRequest.getSupplyId()));

                // Validar stock disponible
                if (supply.getStockActual().compareTo(detailRequest.getQuantity()) < 0) {
                    throw new IllegalArgumentException(
                            "Stock insuficiente para el suministro: " + supply.getNombre() +
                            ". Stock disponible: " + supply.getStockActual() + 
                            ", cantidad requerida: " + detailRequest.getQuantity());
                }

                // Calcular subtotal
                BigDecimal quantity = detailRequest.getQuantity();
                BigDecimal unitPrice = detailRequest.getUnitPrice();
                BigDecimal subtotal = quantity.multiply(unitPrice).setScale(8, RoundingMode.HALF_UP);

                // Crear detalle
                MixtureDetail detail = new MixtureDetail();
                detail.setMixture(savedMixture);
                detail.setSupply(supply);
                detail.setLineNumber(detailRequest.getLineNumber());
                detail.setDescription(detailRequest.getDescription());
                detail.setQuantity(quantity);
                detail.setUnitPrice(unitPrice);
                detail.setSubtotal(subtotal);

                details.add(detail);

                // Restar stock del insumo
                supply.setStockActual(supply.getStockActual().subtract(quantity));
                insumoRepository.save(supply);
            }

            mixtureDetailRepository.saveAll(details);
            savedMixture.setDetails(details);
        }

        return toResponse(savedMixture);
    }

    private BigDecimal resolveTotalCost(BigDecimal requestedTotalCost, List<MixtureDetailRequest> detailRequests) {
        if (detailRequests == null || detailRequests.isEmpty()) {
            if (requestedTotalCost == null) {
                throw new IllegalArgumentException("totalCost es obligatorio cuando no se envían detalles");
            }
            if (requestedTotalCost.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("totalCost debe ser mayor que cero");
            }
            return requestedTotalCost.setScale(8, RoundingMode.HALF_UP);
        }

        BigDecimal computedTotal = detailRequests.stream()
                .map(detail -> detail.getQuantity().multiply(detail.getUnitPrice()).setScale(8, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(8, RoundingMode.HALF_UP);

        return computedTotal;
    }

    private MixtureResponse toResponse(Mixture mixture) {
        List<MixtureDetailResponse> detailResponses = mixture.getDetails() == null
                ? List.of()
                : mixture.getDetails().stream()
                        .map(detail -> new MixtureDetailResponse(
                                detail.getMixtureDetailId(),
                                detail.getLineNumber(),
                                detail.getSupply().getIdInsumo(),
                                detail.getDescription(),
                                detail.getQuantity(),
                                detail.getUnitPrice(),
                                detail.getSubtotal()))
                        .toList();

        return new MixtureResponse(
                mixture.getMixtureId(),
                mixture.getFormula().getFormulaId(),
                mixture.getFormula().getName(),
                mixture.getDefaultQuantity(),
                mixture.getPercentageQuantity(),
                mixture.getProductionTime(),
                mixture.getStatus(),
                mixture.getRegistrationDate(),
                mixture.getTotalCost(),
                detailResponses);
    }
}
