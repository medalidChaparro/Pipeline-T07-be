package vallegrande.edu.pe.AgroTecno.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CollectionDetailRequest {

    @NotNull(message = "lineNumber es obligatorio")
    @Min(value = 1, message = "lineNumber debe ser mayor o igual a 1")
    @Schema(description = "Número de línea del detalle", example = "1")
    private Integer lineNumber;

    @NotBlank(message = "description es obligatorio")
    @Schema(description = "Descripción del concepto del detalle", example = "Semilla maíz")
    private String description;

    @NotNull(message = "quantity es obligatorio")
    @Positive(message = "quantity debe ser mayor que cero")
    @Schema(description = "Cantidad del detalle", example = "2")
    private BigDecimal quantity;

    @NotNull(message = "unitPrice es obligatorio")
    @Positive(message = "unitPrice debe ser mayor que cero")
    @Schema(description = "Precio unitario del detalle", example = "75.25")
    private BigDecimal unitPrice;
}
