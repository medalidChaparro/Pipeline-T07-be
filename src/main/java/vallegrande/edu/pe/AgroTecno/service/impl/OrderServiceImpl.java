package vallegrande.edu.pe.AgroTecno.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import vallegrande.edu.pe.AgroTecno.dto.OrderDetailRequest;
import vallegrande.edu.pe.AgroTecno.dto.OrderDetailResponse;
import vallegrande.edu.pe.AgroTecno.dto.OrderRequest;
import vallegrande.edu.pe.AgroTecno.dto.OrderResponse;
import vallegrande.edu.pe.AgroTecno.model.Client;
import vallegrande.edu.pe.AgroTecno.model.Formula;
import vallegrande.edu.pe.AgroTecno.model.Order;
import vallegrande.edu.pe.AgroTecno.model.OrderDetail;
import vallegrande.edu.pe.AgroTecno.repository.ClientRepository;
import vallegrande.edu.pe.AgroTecno.repository.FormulaRepository;
import vallegrande.edu.pe.AgroTecno.repository.OrderRepository;
import vallegrande.edu.pe.AgroTecno.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final FormulaRepository formulaRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ClientRepository clientRepository,
                            FormulaRepository formulaRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.formulaRepository = formulaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado con ID: " + id));
        return toResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse save(OrderRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente no encontrado con ID: " + request.getClientId()));

        Order order = new Order();
        order.setClient(client);
        order.setReferralGuide(request.getReferralGuide());
        order.setEntryDate(request.getEntryDate() == null
                ? LocalDateTime.now(ZoneId.of("America/Lima"))
                : request.getEntryDate());
        order.setDeliveryDate(request.getDeliveryDate());
        order.setOrderStatus(resolveOrderStatus(request.getOrderStatus()));
        order.setStatus(true);

        List<OrderDetail> details = new ArrayList<>();
        for (OrderDetailRequest detailRequest : request.getDetails()) {
            Formula formula = formulaRepository.findById(detailRequest.getFormulaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formula no encontrada con ID: " + detailRequest.getFormulaId()));

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setFormula(formula);
            detail.setQuantity(detailRequest.getQuantity());
            detail.setSalePrice(detailRequest.getSalePrice());
            details.add(detail);
        }

        order.setDetails(details);
        Order savedOrder = orderRepository.save(order);
        return toResponse(savedOrder);
    }

    private String resolveOrderStatus(String requestedStatus) {
        String orderStatus = requestedStatus == null || requestedStatus.isBlank()
                ? "P"
                : requestedStatus.trim().toUpperCase();

        if (orderStatus.length() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "orderStatus debe tener un unico caracter");
        }

        return orderStatus;
    }

    private OrderResponse toResponse(Order order) {
        List<OrderDetailResponse> detailResponses = order.getDetails() == null
                ? List.of()
                : order.getDetails().stream()
                        .map(detail -> new OrderDetailResponse(
                                detail.getOrderDetailId(),
                                detail.getFormula().getFormulaId(),
                                detail.getFormula().getName(),
                                detail.getQuantity(),
                                detail.getSalePrice()))
                        .toList();

        return new OrderResponse(
                order.getOrderId(),
                order.getClient().getClientId(),
                order.getClient().getName(),
                order.getClient().getLastName(),
                order.getReferralGuide(),
                order.getEntryDate(),
                order.getDeliveryDate(),
                order.getOrderStatus(),
                Boolean.TRUE.equals(order.getStatus()) ? "A" : "I",
                detailResponses);
    }
}
