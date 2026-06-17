package vallegrande.edu.pe.AgroTecno.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MixtureRequest {

    @NotNull(message = "formulaId es obligatorio")
    @Positive(message = "formulaId debe ser mayor que cero")
    @Schema(description = "ID de la fórmula relacionada con la mezcla", example = "3")
    private Integer formulaId;

    @NotNull(message = "defaultQuantity es obligatorio")
    @Positive(message = "defaultQuantity debe ser mayor que cero")
    @Schema(description = "Cantidad por defecto de la mezcla", example = "100.00")
    private BigDecimal defaultQuantity;

    @NotNull(message = "percentageQuantity es obligatorio")
    @Min(value = 0, message = "percentageQuantity debe ser mayor o igual a 0")
    @Schema(description = "Porcentaje de cantidad adicional", example = "10.00", defaultValue = "0")
    private BigDecimal percentageQuantity;

    @Schema(description = "Tiempo de producción en minutos", example = "30")
    private Integer productionTime;

    @Schema(description = "Estado de la mezcla. Ejemplos: A activa, I inactiva, P pendiente", example = "A", defaultValue = "A")
    private String status;

    @Positive(message = "totalCost debe ser mayor que cero cuando no se envían detalles")
    @Schema(description = "Costo total de la mezcla. Si se envían detalles, puede omitirse y se calcula automáticamente.", example = "500.50")
    private BigDecimal totalCost;

    @Valid
    @Schema(description = "Detalle de suministros que conforman la mezcla en una sola acción")
    private List<MixtureDetailRequest> details = new ArrayList<>();
}
