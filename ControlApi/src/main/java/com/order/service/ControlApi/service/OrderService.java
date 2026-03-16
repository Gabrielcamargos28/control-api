package com.order.service.ControlApi.service;

import com.order.service.ControlApi.domain.Order;
import com.order.service.ControlApi.domain.OrderItem;
import com.order.service.ControlApi.dto.OrderItemRequestDTO;
import com.order.service.ControlApi.dto.OrderRequestDTO;
import com.order.service.ControlApi.dto.OrderResponseDTO;
import com.order.service.ControlApi.dto.ProductDTO;
import com.order.service.ControlApi.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Transactional
    public Long createOrder(OrderRequestDTO request) {
        Order order = Order.builder()
                .customerName(request.getNome())
                .customerEmail(request.getEmail())
                .address(request.getEndereço())
                .paymentMethod(request.getFormaPagamento())
                .build();

        for (OrderItemRequestDTO itemRequest : request.getProdutos()) {
            ProductDTO product = productService.getProductById(itemRequest.getIdProduto());
            if (product == null) {
                throw new RuntimeException("Produto não encontrado: " + itemRequest.getIdProduto());
            }

            OrderItem orderItem = OrderItem.builder()
                    .productId(product.getId())
                    .productName(product.getNome())
                    .priceAtPurchase(product.getPreço())
                    .quantity(itemRequest.getQuantidade())
                    .order(order)
                    .build();
            
            order.addItem(orderItem);
        }

        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));

        return convertToResponseDTO(order);
    }

    private OrderResponseDTO convertToResponseDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .nome(order.getCustomerName())
                .email(order.getCustomerEmail())
                .endereço(order.getAddress())
                .formaPagamento(order.getPaymentMethod())
                .itens(order.getItems().stream().map(item -> 
                    OrderResponseDTO.OrderItemResponseDTO.builder()
                        .produtoId(item.getProductId())
                        .nomeProduto(item.getProductName())
                        .preço(item.getPriceAtPurchase())
                        .quantidade(item.getQuantity())
                        .build()
                ).collect(Collectors.toList()))
                .build();
    }
}
