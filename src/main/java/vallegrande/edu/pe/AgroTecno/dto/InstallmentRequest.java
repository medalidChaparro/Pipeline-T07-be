package vallegrande.edu.pe.AgroTecno.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class InstallmentRequest {

    @NotNull(message = "amountPaid es obligatorio")
    @Positive(message = "amountPaid debe ser mayor que cero")
    @Schema(description = "Monto pagado en la cuota", example = "50.00")
    private BigDecimal amountPaid;

    @NotBlank(message = "paymentType es obligatorio")
    @Schema(description = "Tipo de pago", example = "EFECTIVO")
    private String paymentType;

    @NotBlank(message = "paymentDate es obligatorio")
    @Schema(description = "Fecha del pago", example = "2026-06-10 14:00")
    private String paymentDate;

    @Schema(description = "Observación o nota asociada a la cuota", example = "Pago parcial")
    private String observation;

    @NotNull(message = "collectionCollectionId es obligatorio")
    @Schema(description = "ID de la colección asociada", example = "3")
    private Integer collectionCollectionId;
}
