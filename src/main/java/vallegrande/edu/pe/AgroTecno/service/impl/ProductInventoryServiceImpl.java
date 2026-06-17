package vallegrande.edu.pe.AgroTecno.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.edu.pe.AgroTecno.model.ProductInventory;
import vallegrande.edu.pe.AgroTecno.repository.ProductInventoryRepository;
import vallegrande.edu.pe.AgroTecno.service.ProductInventoryService;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private static final Logger log = LoggerFactory.getLogger(ProductInventoryServiceImpl.class);
    private final ProductInventoryRepository inventoryRepository;

    @Autowired
    public ProductInventoryServiceImpl(ProductInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductInventory> findAll() {
        log.info("Listando todo el inventario de productos");
        return inventoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductInventory> findById(Integer id) {
        log.info("Buscando registro de inventario por ID: " + id);
        return inventoryRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductInventory> findByFormulaId(Integer formulaId) {
        log.info("Listando inventario por Formula ID: " + formulaId);
        return inventoryRepository.findByFormulaFormulaId(formulaId);
    }

    @Override
    @Transactional
    public ProductInventory save(ProductInventory productInventory) {
        log.info("Registrando inventario para la fórmula: " + productInventory.getFormula().getFormulaId());
        productInventory.setLastUpdate(LocalDateTime.now());
        return inventoryRepository.save(productInventory);
    }

    @Override
    @Transactional
    public ProductInventory update(ProductInventory productInventory) {
        log.info("Editando inventario ID: " + productInventory.getProductInventoryId());
        productInventory.setLastUpdate(LocalDateTime.now());
        return inventoryRepository.save(productInventory);
    }

    @Override
    @Transactional
    public ProductInventory delete(Integer id) {
        log.info("Deshabilitando lógicamente (PATCH) el inventario ID: " + id);
        ProductInventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de inventario no encontrado con ID: " + id));
        
        inventory.setStatus(false); // Eliminado lógico seguro
        inventory.setLastUpdate(LocalDateTime.now());
        return inventoryRepository.save(inventory);
    }
}