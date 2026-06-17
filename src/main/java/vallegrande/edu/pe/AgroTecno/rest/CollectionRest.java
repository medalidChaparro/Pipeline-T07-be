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
import vallegrande.edu.pe.AgroTecno.dto.CollectionRequest;
import vallegrande.edu.pe.AgroTecno.dto.CollectionResponse;
import vallegrande.edu.pe.AgroTecno.service.CollectionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/collection")
@Tag(name = "Collection API", description = "API para la gestión de transacciones collection")
public class CollectionRest {

    private final CollectionService collectionService;

    @Autowired
    public CollectionRest(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las transacciones collection")
    public List<CollectionResponse> findAll() {
        return collectionService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una transacción collection por ID")
    public CollectionResponse findById(@PathVariable Integer id) {
        return collectionService.findById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "Registrar una transacción collection")
    public CollectionResponse save(@Valid @RequestBody CollectionRequest request) {
        return collectionService.save(request);
    }
}
