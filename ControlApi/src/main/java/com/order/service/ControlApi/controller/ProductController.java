package com.order.service.ControlApi.controller;

import com.order.service.ControlApi.dto.ProductDTO;
import com.order.service.ControlApi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "Endpoints for managing products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products.")
    public List<ProductDTO> getAllProducts() {
        return productService.getProducts();
    }
}
