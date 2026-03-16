package com.order.service.ControlApi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.service.ControlApi.domain.PaymentMethod;
import com.order.service.ControlApi.dto.OrderItemRequestDTO;
import com.order.service.ControlApi.dto.OrderRequestDTO;
import com.order.service.ControlApi.dto.ProductDTO;
import com.order.service.ControlApi.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    void shouldCreateOrderAndReturn201() throws Exception {
        ProductDTO product = ProductDTO.builder()
                .id(1L)
                .nome("Test Product")
                .preço(100.0)
                .build();

        OrderItemRequestDTO itemRequest = OrderItemRequestDTO.builder()
                .id(1L)
                .quantidade(1)
                .build();

        OrderRequestDTO request = OrderRequestDTO.builder()
                .nome("Customer Name")
                .email("customer@test.com")
                .endereço("Address 123")
                .formaPagamento(PaymentMethod.PIX)
                .produtos(List.of(itemRequest))
                .build();

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenDataIsInvalid() throws Exception {
        OrderRequestDTO request = OrderRequestDTO.builder()
                .nome("") // Invalid name
                .build();

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
