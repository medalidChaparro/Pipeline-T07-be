package vallegrande.edu.pe.AgroTecno.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentResponse {

    private Integer installmentId;
    private BigDecimal amountPaid;
    private String paymentType;
    private String paymentDate;
    private String observation;
    private Integer collectionCollectionId;
    private String deletedAt;
}
