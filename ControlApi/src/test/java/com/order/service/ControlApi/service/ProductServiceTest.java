package com.order.service.ControlApi.service;

import com.order.service.ControlApi.domain.Product;
import com.order.service.ControlApi.dto.ProductDTO;
import com.order.service.ControlApi.dto.ProductExternalDTO;
import com.order.service.ControlApi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(restTemplate, productRepository);
    }

    @Test
    void shouldReturnNormalizedProductsWhenApiResponds() {
        ProductExternalDTO externalProduct = new ProductExternalDTO();
        externalProduct.setId(1L);
        externalProduct.setTitle("Test Product");
        externalProduct.setPrice(10.0);
        externalProduct.setDescription("Description");
        externalProduct.setImage("image.png");

        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .price(10.0)
                .description("Description")
                .imageUrl("image.png")
                .stock(10)
                .build();

        when(productRepository.count()).thenReturn(0L);
        when(restTemplate.getForObject(anyString(), eq(ProductExternalDTO[].class)))
                .thenReturn(new ProductExternalDTO[]{externalProduct});
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductDTO> products = productService.getProducts();

        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getNome());
        assertEquals(10.0, products.get(0).getPreço());
        assertNotNull(products.get(0).getEstoque());
        
        verify(productRepository).saveAll(anyList());
    }

    @Test
    void shouldReturnEmptyListWhenApiReturnsNull() {
        when(productRepository.count()).thenReturn(0L);
        when(restTemplate.getForObject(anyString(), eq(ProductExternalDTO[].class)))
                .thenReturn(null);
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<ProductDTO> products = productService.getProducts();

        assertTrue(products.isEmpty());
    }

    @Test
    void shouldReturnFromRepositoryIfNotEmpty() {
        Product product = Product.builder()
                .id(1L)
                .name("Repo Product")
                .price(20.0)
                .stock(5)
                .build();

        when(productRepository.count()).thenReturn(1L);
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductDTO> products = productService.getProducts();

        assertEquals(1, products.size());
        assertEquals("Repo Product", products.get(0).getNome());
        verify(restTemplate, never()).getForObject(anyString(), any());
    }

    @Test
    void shouldDeductStockWhenAvailable() {
        Product product = Product.builder()
                .id(1L)
                .name("Product Test")
                .stock(10)
                .build();

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));

        productService.deductStock(1L, 3);

        assertEquals(7, product.getStock());
        verify(productRepository).save(product);
    }

    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {
        Product product = Product.builder()
                .id(1L)
                .name("Product Test")
                .stock(2)
                .build();

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));

        assertThrows(RuntimeException.class, () -> productService.deductStock(1L, 5));
        verify(productRepository, never()).save(any());
    }
}
