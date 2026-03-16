package com.order.service.ControlApi.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductExternalDTO {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}
