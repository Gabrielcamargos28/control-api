package com.order.service.ControlApi.controller;

import com.order.service.ControlApi.dto.OrderRequestDTO;
import com.order.service.ControlApi.dto.OrderResponseDTO;
import com.order.service.ControlApi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Controller", description = "Endpoints for managing orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Create a new order", description = "Creates an order based on the provided request body and returns the new order ID.")
    @ApiResponse(responseCode = "201", description = "Order created successfully")
    public ResponseEntity<Long> createOrder(@Valid @RequestBody OrderRequestDTO request) {
        Long orderId = orderService.createOrder(request);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieves details for all orders.")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an order by ID", description = "Retrieves order details for the specified order ID.")
    @ApiResponse(responseCode = "200", description = "Order found")
    @ApiResponse(responseCode = "404", description = "Order not found")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
