package com.order.service.ControlApi.domain;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    PIX("Pix"),
    CARD("Cartão"),
    BILL("Boleto");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }
}
