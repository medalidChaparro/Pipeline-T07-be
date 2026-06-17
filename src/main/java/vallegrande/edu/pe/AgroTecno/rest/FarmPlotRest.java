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
import vallegrande.edu.pe.AgroTecno.model.FarmPlot;
import vallegrande.edu.pe.AgroTecno.service.FarmPlotService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/farmplot")
@Tag(name = "FarmPlot API", description = "API para la gestión de Parcelas Agrícolas")
public class FarmPlotRest {

    private final FarmPlotService farmPlotService;

    @Autowired
    public FarmPlotRest(FarmPlotService farmPlotService) {
        this.farmPlotService = farmPlotService;
    }

    // GET - Listar todos
    @GetMapping
    @Operation(summary = "Listar todas las Parcelas Agrícolas")
    public ResponseEntity<List<FarmPlot>> findAll() {
        List<FarmPlot> farmPlots = farmPlotService.findAll();
        return ResponseEntity.ok(farmPlots);
    }

    // GET - Buscar por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar Parcela Agrícola por ID")
    public ResponseEntity<FarmPlot> findById(@PathVariable Integer id) {
        Optional<FarmPlot> farmPlot = farmPlotService.findById(id);
        return farmPlot.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET - Buscar por Customer ID
    @GetMapping("/customer/{clientId}")
    @Operation(summary = "Listar Parcelas por Cliente")
    public ResponseEntity<List<FarmPlot>> findByCustomerClientId(@PathVariable Integer clientId) {
        List<FarmPlot> farmPlots = farmPlotService.findByCustomerClientId(clientId);
        return ResponseEntity.ok(farmPlots);
    }

    // POST - Crear
    @PostMapping
    @Operation(summary = "Crear una Parcela Agrícola")
    public ResponseEntity<FarmPlot> save(@RequestBody FarmPlot farmPlot) {
        FarmPlot saved = farmPlotService.save(farmPlot);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT - Actualizar
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una Parcela Agrícola")
    public ResponseEntity<FarmPlot> update(@PathVariable Integer id, @RequestBody FarmPlot farmPlot) {
        Optional<FarmPlot> existing = farmPlotService.findById(id);
        if (existing.isPresent()) {
            farmPlot.setFarmId(id);
            FarmPlot updated = farmPlotService.update(farmPlot);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Eliminar lógico
    @PatchMapping("/{id}/delete")
    @Operation(summary = "Eliminar lógicamente una Parcela Agrícola")
    public ResponseEntity<FarmPlot> delete(@PathVariable Integer id) {
        Optional<FarmPlot> existing = farmPlotService.findById(id);
        if (existing.isPresent()) {
            FarmPlot deleted = farmPlotService.delete(id);
            return ResponseEntity.ok(deleted);
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Restaurar
    @PatchMapping("/{id}/restore")
    @Operation(summary = "Restaurar una Parcela Agrícola eliminada")
    public ResponseEntity<FarmPlot> restore(@PathVariable Integer id) {
        Optional<FarmPlot> existing = farmPlotService.findById(id);
        if (existing.isPresent()) {
            FarmPlot restored = farmPlotService.restore(id);
            return ResponseEntity.ok(restored);
        }
        return ResponseEntity.notFound().build();
    }

}
