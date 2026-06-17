package vallegrande.edu.pe.AgroTecno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vallegrande.edu.pe.AgroTecno.model.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    // Aquí se concentran las operaciones CRUD del detalle del pedido
}