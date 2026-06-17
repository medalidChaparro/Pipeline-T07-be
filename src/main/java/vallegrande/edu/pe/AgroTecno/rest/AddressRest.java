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
import vallegrande.edu.pe.AgroTecno.model.Address;
import vallegrande.edu.pe.AgroTecno.service.AddressService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/address")
@Tag(name = "Address API", description = "API para la gestión de Direcciones")
public class AddressRest {

    private final AddressService addressService;

    @Autowired
    public AddressRest(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las direcciones")
    public List<Address> findAll() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar dirección por ID")
    public Address findById(@PathVariable Integer id) {
        return addressService.findById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "Crear dirección")
    public Address save(@RequestBody Address address) {
        return addressService.save(address);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar dirección")
    public Address update(@PathVariable Integer id, @RequestBody Address address) {
        address.setAddressId(id);
        return addressService.update(address);
    }

    @PatchMapping("/delete/{id}")
    @Operation(summary = "Eliminar dirección")
    public void delete(@PathVariable Integer id) {
        addressService.delete(id);
    }
}
