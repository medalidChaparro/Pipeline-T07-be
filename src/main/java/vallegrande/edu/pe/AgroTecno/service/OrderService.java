package vallegrande.edu.pe.AgroTecno.service;

import java.util.List;
import vallegrande.edu.pe.AgroTecno.dto.OrderRequest;
import vallegrande.edu.pe.AgroTecno.dto.OrderResponse;

public interface OrderService {
    
    List<OrderResponse> findAll();
    
    OrderResponse findById(Integer id);
    
    OrderResponse save(OrderRequest request);
}