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
public class CollectionResponse {

    private Integer collectionId;
    private BigDecimal totalAmount;
    private Integer installmentsCount;
    private String paymentStatus;
    private LocalDateTime registrationDate;
    private Integer orderOrderId;
    private List<CollectionDetailResponse> details = new ArrayList<>();
}
