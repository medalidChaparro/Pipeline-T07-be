package vallegrande.edu.pe.AgroTecno.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import vallegrande.edu.pe.AgroTecno.model.Formula;
import vallegrande.edu.pe.AgroTecno.service.FormulaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/formula")
@Tag(name = "Formula API", description = "API para la gestión de Fórmulas")
public class FormulaRest {

    private final FormulaService formulaService;

    @Autowired
    public FormulaRest(FormulaService formulaService) {
        this.formulaService = formulaService;
    }

    // GET - Listar todos (Ruta: GET /formula)
    @GetMapping
    @Operation(summary = "Listar todas las Fórmulas")
    public List<Formula> findAll(){
        return formulaService.findAll();
    }

    // GET - Listar por estado (Ruta: GET /formula/estado/true)
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar por estado")
    public List<Formula> findByEstado(@PathVariable Boolean estado) {
        return formulaService.findByEstado(estado);
    }

    // GET - Buscar por ID (Ruta: GET /formula/1)
    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID")
    public Formula findById(@PathVariable Integer id) {
        return formulaService.findById(id);
    }

    // POST - Crear (Ruta: POST /formula) -> ¡Quitamos el /save!
    @PostMapping
    @Operation(summary = "Crear Fórmula")
    public Formula save(@RequestBody Formula formula) {
        return formulaService.save(formula);
    }

    // PUT - Actualizar (Ruta: PUT /formula/1) -> ¡Quitamos el /update!
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Fórmula")
    public Formula update(@PathVariable Integer id, @RequestBody Formula formula) {
        formula.setFormulaId(id);
        return formulaService.update(formula);
    }

    // PATCH - Eliminar lógico (Ruta: PATCH /formula/delete/1)
    @PatchMapping("/delete/{id}")
    @Operation(summary = "Eliminar lógico")
    public Formula delete(@PathVariable Integer id) {
        return formulaService.delete(id);
    }

    // PATCH - Restaurar (Ruta: PATCH /formula/restore/1)
    @PatchMapping("/restore/{id}")
    @Operation(summary = "Restaurar")
    public Formula restore(@PathVariable Integer id) {
        return formulaService.restore(id);
    }
}