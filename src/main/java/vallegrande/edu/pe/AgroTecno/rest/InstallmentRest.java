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
import jakarta.validation.Valid;
import vallegrande.edu.pe.AgroTecno.dto.InstallmentRequest;
import vallegrande.edu.pe.AgroTecno.dto.InstallmentResponse;
import vallegrande.edu.pe.AgroTecno.service.InstallmentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/installment")
@Tag(name = "Installment API", description = "API para la gestión de transacciones installment")
public class InstallmentRest {

    private final InstallmentService installmentService;

    @Autowired
    public InstallmentRest(InstallmentService installmentService) {
        this.installmentService = installmentService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las transacciones installment")
    public List<InstallmentResponse> findAll() {
        return installmentService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una transacción installment por ID")
    public InstallmentResponse findById(@PathVariable Integer id) {
        return installmentService.findById(id);
    }

    @GetMapping("/collection/{collectionId}")
    @Operation(summary = "Listar las transacciones installment asociadas a una collection")
    public List<InstallmentResponse> findByCollectionId(@PathVariable Integer collectionId) {
        return installmentService.findByCollectionId(collectionId);
    }

    @PostMapping("/save")
    @Operation(summary = "Registrar una transacción installment")
    public InstallmentResponse save(@Valid @RequestBody InstallmentRequest request) {
        return installmentService.save(request);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar una transacción installment")
    public InstallmentResponse update(@PathVariable Integer id, @Valid @RequestBody InstallmentRequest request) {
        return installmentService.update(id, request);
    }

    @PatchMapping("/delete/{id}")
    @Operation(summary = "Eliminar lógico de una transacción installment")
    public InstallmentResponse delete(@PathVariable Integer id) {
        return installmentService.delete(id);
    }

    @PatchMapping("/restore/{id}")
    @Operation(summary = "Restaurar una transacción installment")
    public InstallmentResponse restore(@PathVariable Integer id) {
        return installmentService.restore(id);
    }
}
