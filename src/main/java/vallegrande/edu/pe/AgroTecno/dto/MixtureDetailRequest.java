package vallegrande.edu.pe.AgroTecno.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MixtureDetailRequest {

    @NotNull(message = "lineNumber es obligatorio")
    @Min(value = 1, message = "lineNumber debe ser mayor o igual a 1")
    @Schema(description = "Número de línea del detalle de la mezcla", example = "1")
    private Integer lineNumber;

    @NotNull(message = "supplyId es obligatorio")
    @Positive(message = "supplyId debe ser mayor que cero")
    @Schema(description = "ID del suministro/insumo usado en la mezcla", example = "5")
    private Integer supplyId;

    @NotBlank(message = "description es obligatorio")
    @Schema(description = "Descripción del suministro en el detalle", example = "Fungicida tipo A")
    private String description;

    @NotNull(message = "quantity es obligatorio")
    @Positive(message = "quantity debe ser mayor que cero")
    @Schema(description = "Cantidad del suministro en la mezcla", example = "2.5")
    private BigDecimal quantity;

    @NotNull(message = "unitPrice es obligatorio")
    @Positive(message = "unitPrice debe ser mayor que cero")
    @Schema(description = "Precio unitario del suministro", example = "150.75")
    private BigDecimal unitPrice;
}
