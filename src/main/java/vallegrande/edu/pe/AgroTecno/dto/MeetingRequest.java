package vallegrande.edu.pe.AgroTecno.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MeetingRequest {

    @NotNull(message = "El campo clientId es obligatorio")
    @Positive(message = "El campo clientId debe ser un ID válido mayor a cero")
    @Schema(description = "ID del cliente", example = "1")
    private Integer clientId;

    @NotNull(message = "El campo meetingDate es obligatorio")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Fecha de la reunión", example = "2026-05-24")
    private LocalDate meetingDate;

    @NotNull(message = "El campo meetingTime es obligatorio")
    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(description = "Hora de la reunión", example = "10:30:00")
    private LocalTime meetingTime;

    @Schema(description = "Calidad de la fruta observada", example = "Excelente")
    private String fruitQuality;

    @Schema(description = "Observaciones generales de la reunión", example = "Cultivo en buenas condiciones")
    private String observation;

    @NotNull(message = "El campo visitStatus es obligatorio")
    @Schema(description = "Estado de la visita ('P' = Pendiente, 'R' = Realizada, 'C' = Cancelada)", example = "R", defaultValue = "P")
    private String visitStatus;

    @NotNull(message = "El campo productionOrderId es obligatorio")
    @Positive(message = "El campo productionOrderId debe ser un ID válido mayor a cero")
    @Schema(description = "ID de la orden de producción", example = "1")
    private Integer productionOrderId;

    @NotNull(message = "El campo farmId es obligatorio")
    @Positive(message = "El campo farmId debe ser un ID válido mayor a cero")
    @Schema(description = "ID de la parcela/finca", example = "1")
    private Integer farmId;
}
