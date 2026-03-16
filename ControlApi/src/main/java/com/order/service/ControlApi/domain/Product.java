package com.order.service.ControlApi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    private String imageUrl;

    // Manual Builder
    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private Long id;
        private String name;
        private String description;
        private Double price;
        private Integer stock;
        private String imageUrl;

        public ProductBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder price(Double price) {
            this.price = price;
            return this;
        }

        public ProductBuilder stock(Integer stock) {
            this.stock = stock;
            return this;
        }

        public ProductBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.setId(this.id);
            product.setName(this.name);
            product.setDescription(this.description);
            product.setPrice(this.price);
            product.setStock(this.stock);
            product.setImageUrl(this.imageUrl);
            return product;
        }
    }
}
