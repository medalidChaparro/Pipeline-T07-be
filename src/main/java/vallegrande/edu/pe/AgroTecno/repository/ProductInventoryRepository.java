package vallegrande.edu.pe.AgroTecno.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vallegrande.edu.pe.AgroTecno.model.ProductInventory;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Integer> {
    
    // Busca las presentaciones e inventarios asociados a una fórmula
    List<ProductInventory> findByFormulaFormulaId(Integer formulaId);
    
}