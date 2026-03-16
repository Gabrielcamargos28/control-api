package com.order.service.ControlApi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Double priceAtPurchase;

    @Column(nullable = false)
    private Integer quantity;

    // Manual Builder
    public static OrderItemBuilder builder() {
        return new OrderItemBuilder();
    }

    public static class OrderItemBuilder {
        private Long id;
        private Order order;
        private Long productId;
        private String productName;
        private Double priceAtPurchase;
        private Integer quantity;

        public OrderItemBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderItemBuilder order(Order order) {
            this.order = order;
            return this;
        }

        public OrderItemBuilder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public OrderItemBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public OrderItemBuilder priceAtPurchase(Double priceAtPurchase) {
            this.priceAtPurchase = priceAtPurchase;
            return this;
        }

        public OrderItemBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItem build() {
            OrderItem item = new OrderItem();
            item.setId(this.id);
            item.setOrder(this.order);
            item.setProductId(this.productId);
            item.setProductName(this.productName);
            item.setPriceAtPurchase(this.priceAtPurchase);
            item.setQuantity(this.quantity);
            return item;
        }
    }
}
