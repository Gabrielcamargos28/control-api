package com.order.service.ControlApi.service;

import com.order.service.ControlApi.domain.Product;
import com.order.service.ControlApi.dto.ProductDTO;
import com.order.service.ControlApi.dto.ProductExternalDTO;
import com.order.service.ControlApi.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final RestTemplate restTemplate;
    private final ProductRepository productRepository;
    private final String API_URL = "https://fakestoreapi.com/products";

    public ProductService(RestTemplate restTemplate, ProductRepository productRepository) {
        this.restTemplate = restTemplate;
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getProducts() {
        // Se o banco estiver vazio, busca da API e salva
        if (productRepository.count() == 0) {
            ProductExternalDTO[] externalProducts = restTemplate.getForObject(API_URL, ProductExternalDTO[].class);
            if (externalProducts != null) {
                List<Product> products = Arrays.stream(externalProducts)
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                productRepository.saveAll(products);
            }
        }

        // Retorna o que estiver no banco
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO)
                .orElseGet(() -> {
                    ProductExternalDTO externalProduct = restTemplate.getForObject(API_URL + "/" + id, ProductExternalDTO.class);
                    if (externalProduct == null) return null;
                    
                    Product product = convertToEntity(externalProduct);
                    productRepository.save(product);
                    return convertToDTO(product);
                });
    }

    private Product convertToEntity(ProductExternalDTO external) {
        return Product.builder()
                .id(external.getId())
                .name(external.getTitle())
                .description(external.getDescription())
                .price(external.getPrice())
                .stock(10) // Estoque simulado
                .imageUrl(external.getImage())
                .build();
    }

    private ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .nome(product.getName())
                .descrição(product.getDescription())
                .preço(product.getPrice())
                .estoque(product.getStock())
                .imagem(product.getImageUrl())
                .build();
    }

    private ProductDTO convertToDTO(ProductExternalDTO external) {
        return ProductDTO.builder()
                .id(external.getId())
                .nome(external.getTitle())
                .descrição(external.getDescription())
                .preço(external.getPrice())
                .estoque(10) // Estoque simulado
                .imagem(external.getImage())
                .build();
    }
}
