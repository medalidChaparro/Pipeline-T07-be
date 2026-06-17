package vallegrande.edu.pe.AgroTecno.dto;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class OrderDetailRequest {

    @NotNull(message = "El campo formulaId es obligatorio")
    @Positive(message = "El campo formulaId debe ser mayor a cero")
    @Schema(description = "ID de la fórmula solicitada", example = "1")
    private Integer formulaId;

    @NotNull(message = "El campo quantity es obligatorio")
    @Positive(message = "La cantidad (quantity) debe ser mayor a cero")
    @Schema(description = "Cantidad total solicitada de la fórmula", example = "10")
    private Integer quantity;

    @NotNull(message = "El campo salePrice es obligatorio")
    @PositiveOrZero(message = "El precio de venta (salePrice) no puede ser negativo")
    @Schema(description = "Precio unitario histórico de venta para este pedido", example = "150.00")
    private BigDecimal salePrice;
}