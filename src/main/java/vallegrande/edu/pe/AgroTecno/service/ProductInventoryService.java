package vallegrande.edu.pe.AgroTecno.service;

import java.util.List;
import java.util.Optional;
import vallegrande.edu.pe.AgroTecno.model.ProductInventory;

public interface ProductInventoryService {

    List<ProductInventory> findAll();

    Optional<ProductInventory> findById(Integer id);

    List<ProductInventory> findByFormulaId(Integer formulaId);

    ProductInventory save(ProductInventory productInventory);

    ProductInventory update(ProductInventory productInventory);

    ProductInventory delete(Integer id); // Regresa a llamarse "delete"

}