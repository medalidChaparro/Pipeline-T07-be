package vallegrande.edu.pe.AgroTecno.rest;

import vallegrande.edu.pe.AgroTecno.model.Insumo;
import vallegrande.edu.pe.AgroTecno.service.InsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/insumo")
@Tag(name = "Insumo API", description = "API para la gestión de Insumos")
public class InsumoRest {

    private final InsumoService insumoService;

    @Autowired
    public InsumoRest(InsumoService insumoService) {
        this.insumoService = insumoService;
    }

    // GET - Listar todos
    @GetMapping
    @Operation(summary = "Listar todos los Insumos")
    public List<Insumo> findAll(){
        return insumoService.findAll();
    }

    // GET - Listar por estado
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar por estado")
    public List<Insumo> findByEstado(@PathVariable Boolean estado) {
        return insumoService.findByEstado(estado);
    }

    // GET - Buscar por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID")
    public Optional<Insumo> findById(@PathVariable Integer id) {
        return insumoService.findById(id);
    }

    // POST - Crear
    @PostMapping("/save")
    @Operation(summary = "Crear Insumo")
    public Insumo save(@RequestBody Insumo insumo) {
        return insumoService.save(insumo);
    }

    // PUT - Actualizar
    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar Insumo")
    public Insumo update(@PathVariable Integer id, @RequestBody Insumo insumo) {
        insumo.setIdInsumo(id);
        return insumoService.update(insumo);
    }

    // PATCH - Eliminar lógico
    @PatchMapping("/delete/{id}")
    @Operation(summary = "Eliminar lógico")
    public Insumo delete(@PathVariable Integer id) {
        return insumoService.delete(id);
    }

    // PATCH - Restaurar
    @PatchMapping("/restore/{id}")
    @Operation(summary = "Restaurar")
    public Insumo restore(@PathVariable Integer id) {
        return insumoService.restore(id);
    }
}