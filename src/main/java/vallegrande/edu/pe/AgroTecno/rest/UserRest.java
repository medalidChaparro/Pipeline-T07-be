package vallegrande.edu.pe.AgroTecno.rest;

import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import vallegrande.edu.pe.AgroTecno.model.User;
import vallegrande.edu.pe.AgroTecno.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "API para la autenticación y gestión de Usuarios")
public class UserRest {

    private final UserService userService;

    @Autowired
    public UserRest(UserService userService) {
        this.userService = userService;
    }

    // GET - Listar activos
    @GetMapping
    @Operation(summary = "Listar todos los usuarios activos")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAllActive());
    }

    // POST - Crear
    @PostMapping
    @Operation(summary = "Crear un nuevo usuario")
    public ResponseEntity<User> save(@Valid @RequestBody User user) {
        User saved = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT - Actualizar
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de un usuario")
    public ResponseEntity<User> update(@PathVariable Integer id, @Valid @RequestBody User user) {
        Optional<User> existing = userService.findById(id);
        if (existing.isPresent()) {
            user.setUserId(id);
            User updated = userService.update(user);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Eliminación Lógica
    @PatchMapping("/{id}/disable")
    @Operation(summary = "Deshabilitar lógicamente un usuario (PATCH)")
    public ResponseEntity<User> deleteLogical(@PathVariable Integer id) {
        Optional<User> existing = userService.findById(id);
        if (existing.isPresent()) {
            User disabledUser = userService.deleteLogical(id);
            return ResponseEntity.ok(disabledUser);
        }
        return ResponseEntity.notFound().build();
    }
}