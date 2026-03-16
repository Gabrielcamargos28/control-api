package com.order.service.ControlApi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
    @NotNull(message = "ID do produto é obrigatório")
    private Long idProduto;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade mínima é 1")
    private Integer quantidade;

    // Manual Builder
    public static OrderItemRequestDTOBuilder builder() {
        return new OrderItemRequestDTOBuilder();
    }

    public static class OrderItemRequestDTOBuilder {
        private Long id;
        private Integer quantidade;

        public OrderItemRequestDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderItemRequestDTOBuilder quantidade(Integer quantidade) {
            this.quantidade = quantidade;
            return this;
        }

        public OrderItemRequestDTO build() {
            OrderItemRequestDTO dto = new OrderItemRequestDTO();
            dto.setIdProduto(this.id);
            dto.setQuantidade(this.quantidade);
            return dto;
        }
    }
}
