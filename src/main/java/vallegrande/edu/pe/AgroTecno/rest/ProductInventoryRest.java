package vallegrande.edu.pe.AgroTecno.rest;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import vallegrande.edu.pe.AgroTecno.model.ProductInventory;
import vallegrande.edu.pe.AgroTecno.service.ProductInventoryService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/productinventory")
@Tag(name = "ProductInventory API", description = "API para la gestión del Stock e Inventario de Fórmulas (Presentaciones 1L y 20L)")
public class ProductInventoryRest {

    private final ProductInventoryService inventoryService;

    @Autowired
    public ProductInventoryRest(ProductInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    @Operation(summary = "Listar todo el inventario de productos")
    public ResponseEntity<List<ProductInventory>> findAll() {
        List<ProductInventory> inventories = inventoryService.findAll();
        return ResponseEntity.ok(inventories);
    }

    @PostMapping
    @Operation(summary = "Asignar stock a una presentación (1L / 20L) de una Fórmula")
    public ResponseEntity<ProductInventory> save(@RequestBody ProductInventory productInventory) {
        ProductInventory saved = inventoryService.save(productInventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar stock o datos de una presentación")
    public ResponseEntity<ProductInventory> update(@PathVariable Integer id, @RequestBody ProductInventory productInventory) {
        Optional<ProductInventory> existing = inventoryService.findById(id);
        if (existing.isPresent()) {
            productInventory.setProductInventoryId(id);
            ProductInventory updated = inventoryService.update(productInventory);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/disable")
    @Operation(summary = "Deshabilitar lógicamente un registro de inventario (PATCH)")
    public ResponseEntity<ProductInventory> delete(@PathVariable Integer id) {
        Optional<ProductInventory> existing = inventoryService.findById(id);
        if (existing.isPresent()) {
            ProductInventory disabledInventory = inventoryService.delete(id);
            return ResponseEntity.ok(disabledInventory);
        }
        return ResponseEntity.notFound().build();
    }
}