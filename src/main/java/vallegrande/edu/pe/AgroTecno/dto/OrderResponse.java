package vallegrande.edu.pe.AgroTecno.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Integer orderId;
    private Integer clientId;
    private String clientFirstName; // Nombre del cliente (mapeado desde field 'name' de Client.java)
    private String clientLastName;  // Apellido del cliente
    private String referralGuide;
    
    @JsonFormat(pattern = "dd_MM_yyyy HH:mm_ss")
    private LocalDateTime entryDate;
    
    @JsonFormat(pattern = "dd_MM_yyyy HH:mm_ss")
    private LocalDateTime deliveryDate;
    
    private String orderStatus;  // Estado de flujo logístico ('P', 'E', 'C')
    private String status;       // Estado de auditoría en formato de salida string ('A' = Activo, 'I' = Inactivo)
    private List<OrderDetailResponse> details = new ArrayList<>();
}