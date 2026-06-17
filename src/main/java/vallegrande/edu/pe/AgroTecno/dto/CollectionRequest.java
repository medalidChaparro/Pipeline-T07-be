package vallegrande.edu.pe.AgroTecno.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CollectionRequest {

    @Positive(message = "totalAmount debe ser mayor que cero")
    @Schema(description = "Monto total de la transacción. Si se envían detalles, puede omitirse y se calcula automáticamente.", example = "150.50")
    private BigDecimal totalAmount;

    @Min(value = 1, message = "installmentsCount debe ser mayor o igual a 1")
    @Schema(description = "Número de cuotas de la transacción", example = "1", defaultValue = "1")
    private Integer installmentsCount;

    @Schema(description = "Estado del pago. Ejemplos: P pendiente, C cancelado, F finalizado, R reembolsado", example = "P")
    private String paymentStatus;

    @Schema(description = "ID de la orden relacionada. Campo simple sin relación implementada", example = "7")
    private Integer orderOrderId;

    @Valid
    @Schema(description = "Detalle de la transacción en una sola acción")
    private List<CollectionDetailRequest> details = new ArrayList<>();
}
