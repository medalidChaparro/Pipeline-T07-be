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
import vallegrande.edu.pe.AgroTecno.dto.MeetingRequest;
import vallegrande.edu.pe.AgroTecno.dto.MeetingResponse;
import vallegrande.edu.pe.AgroTecno.service.MeetingService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/meeting")
@Tag(name = "Meeting API", description = "API para la gestión de transacciones de reuniones (Meeting)")
public class MeetingRest {

    private final MeetingService meetingService;

    @Autowired
    public MeetingRest(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las transacciones de reuniones")
    public List<MeetingResponse> findAll() {
        return meetingService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una transacción de reunión por ID")
    public MeetingResponse findById(@PathVariable Integer id) {
        return meetingService.findById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "Registrar una transacción de reunión")
    public MeetingResponse save(@Valid @RequestBody MeetingRequest request) {
        return meetingService.save(request);
    }
}
