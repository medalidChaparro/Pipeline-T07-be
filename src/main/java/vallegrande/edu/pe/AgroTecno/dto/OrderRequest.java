package vallegrande.edu.pe.AgroTecno.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderRequest {

    @NotNull(message = "El campo clientId es obligatorio")
    @Positive(message = "El campo clientId debe ser un ID válido mayor a cero")
    @Schema(description = "ID del cliente que realiza la compra", example = "1")
    private Integer clientId;

    @Schema(description = "Código o número correlativo de la Guía de Remisión", example = "GRE-2026-0001")
    private String referralGuide;

    @JsonFormat(pattern = "dd_MM_yyyy HH:mm_ss")
    @Schema(description = "Fecha de registro de entrada del pedido. Si se omite, el sistema usará la hora actual.", example = "24_05_2026 16:00_00")
    private LocalDateTime entryDate;

    @JsonFormat(pattern = "dd_MM_yyyy HH:mm_ss")
    @Schema(description = "Fecha pactada o estimada para realizar la entrega", example = "28_05_2026 10:00_00")
    private LocalDateTime deliveryDate;

    @Schema(description = "Estado del flujo logístico del pedido ('P' = Pendiente, 'E' = Entregado, 'C' = Cancelado)", example = "P", defaultValue = "P")
    private String orderStatus;

    @Valid
    @NotEmpty(message = "El pedido debe contener al menos una línea de detalle")
    @Schema(description = "Colección de fórmulas y cantidades que componen este pedido transaccional")
    private List<OrderDetailRequest> details = new ArrayList<>();
}