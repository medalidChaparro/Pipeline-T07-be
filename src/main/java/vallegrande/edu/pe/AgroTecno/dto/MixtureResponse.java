package vallegrande.edu.pe.AgroTecno.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MixtureResponse {

    private Integer mixtureId;
    private Integer formulaId;
    private String formulaName;
    private BigDecimal defaultQuantity;
    private BigDecimal percentageQuantity;
    private Integer productionTime;
    private String status;
    private LocalDateTime registrationDate;
    private BigDecimal totalCost;
    private List<MixtureDetailResponse> details = new ArrayList<>();
}
