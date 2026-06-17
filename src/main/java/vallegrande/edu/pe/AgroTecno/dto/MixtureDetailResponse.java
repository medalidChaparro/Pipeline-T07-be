package vallegrande.edu.pe.AgroTecno.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MixtureDetailResponse {

    private Integer mixtureDetailId;
    private Integer lineNumber;
    private Integer supplyId;
    private String description;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
