package vallegrande.edu.pe.AgroTecno.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    
    private Integer orderDetailId;
    private Integer formulaId;
    private String formulaName; // Resuelve el nombre descriptivo de la fórmula en la consulta
    private Integer quantity;
    private BigDecimal salePrice;
}