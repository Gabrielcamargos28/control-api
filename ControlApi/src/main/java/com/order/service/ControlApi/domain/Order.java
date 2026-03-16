package com.order.service.ControlApi.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "tb_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    // Manual Builder
    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {
        private Long id;
        private String customerName;
        private String customerEmail;
        private String address;
        private PaymentMethod paymentMethod;
        private List<OrderItem> items = new ArrayList<>();

        public OrderBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderBuilder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public OrderBuilder customerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
            return this;
        }

        public OrderBuilder address(String address) {
            this.address = address;
            return this;
        }

        public OrderBuilder paymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public OrderBuilder items(List<OrderItem> items) {
            this.items = items;
            return this;
        }

        public Order build() {
            Order order = new Order();
            order.setId(this.id);
            order.setCustomerName(this.customerName);
            order.setCustomerEmail(this.customerEmail);
            order.setAddress(this.address);
            order.setPaymentMethod(this.paymentMethod);
            order.setItems(this.items);
            return order;
        }
    }
}
