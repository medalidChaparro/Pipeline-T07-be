package vallegrande.edu.pe.AgroTecno.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingResponse {

    private Integer meetingId;

    private Integer clientId;
    private String clientFirstName;
    private String clientLastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime meetingTime;

    private String fruitQuality;
    private String observation;
    private String visitStatus;
    private Integer productionOrderId;
    private Integer farmId;
}
