package vallegrande.edu.pe.AgroTecno.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import vallegrande.edu.pe.AgroTecno.dto.MixtureRequest;
import vallegrande.edu.pe.AgroTecno.dto.MixtureResponse;
import vallegrande.edu.pe.AgroTecno.service.MixtureService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/mixture")
@Tag(name = "Mixture API", description = "API para la gestión de transacciones mixture")
public class MixtureRest {

    private final MixtureService mixtureService;

    @Autowired
    public MixtureRest(MixtureService mixtureService) {
        this.mixtureService = mixtureService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las transacciones mixture")
    public List<MixtureResponse> findAll() {
        return mixtureService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una transacción mixture por ID")
    public MixtureResponse findById(@PathVariable Integer id) {
        return mixtureService.findById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "Registrar una transacción mixture con sus detalles")
    public MixtureResponse save(@Valid @RequestBody MixtureRequest request) {
        return mixtureService.save(request);
    }
}
