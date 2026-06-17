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
import vallegrande.edu.pe.AgroTecno.dto.OrderRequest;
import vallegrande.edu.pe.AgroTecno.dto.OrderResponse;
import vallegrande.edu.pe.AgroTecno.service.OrderService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order")
@Tag(name = "Order API", description = "API para la gestión de transacciones de pedidos (Order)")
public class OrderRest {

    private final OrderService orderService;

    @Autowired
    public OrderRest(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las transacciones de pedidos")
    public List<OrderResponse> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una transacción de pedido por ID")
    public OrderResponse findById(@PathVariable Integer id) {
        return orderService.findById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "Registrar una transacción de pedido")
    public OrderResponse save(@Valid @RequestBody OrderRequest request) {
        return orderService.save(request);
    }
}