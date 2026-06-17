package vallegrande.edu.pe.AgroTecno.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vallegrande.edu.pe.AgroTecno.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Override
    @EntityGraph(attributePaths = {"client", "details", "details.formula"})
    List<Order> findAll();

    @Override
    @EntityGraph(attributePaths = {"client", "details", "details.formula"})
    Optional<Order> findById(Integer id);
}
