package com.order.service.ControlApi.service;

import com.order.service.ControlApi.domain.Order;
import com.order.service.ControlApi.domain.PaymentMethod;
import com.order.service.ControlApi.dto.OrderItemRequestDTO;
import com.order.service.ControlApi.dto.OrderRequestDTO;
import com.order.service.ControlApi.dto.ProductDTO;
import com.order.service.ControlApi.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository, productService);
    }

    @Test
    void shouldCreateOrderWhenDataIsValid() {
        ProductDTO product = ProductDTO.builder()
                .id(1L)
                .nome("Product Test")
                .preço(50.0)
                .build();

        OrderItemRequestDTO itemRequest = OrderItemRequestDTO.builder()
                .id(1L)
                .quantidade(2)
                .build();

        OrderRequestDTO request = OrderRequestDTO.builder()
                .nome("Gabriel")
                .email("gabriel@test.com")
                .endereço("Rua Teste, 123")
                .formaPagamento(PaymentMethod.PIX)
                .produtos(List.of(itemRequest))
                .build();

        Order savedOrder = Order.builder().id(100L).build();

        when(productService.getProductById(1L)).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        Long orderId = orderService.createOrder(request);

        assertNotNull(orderId);
        assertEquals(100L, orderId);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        OrderItemRequestDTO itemRequest = OrderItemRequestDTO.builder()
                .id(999L)
                .quantidade(1)
                .build();

        OrderRequestDTO request = OrderRequestDTO.builder()
                .nome("Gabriel")
                .email("gabriel@test.com")
                .endereço("Rua Teste, 123")
                .formaPagamento(PaymentMethod.PIX)
                .produtos(List.of(itemRequest))
                .build();

        when(productService.getProductById(999L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> orderService.createOrder(request));
    }
}
