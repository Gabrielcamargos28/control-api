package com.order.service.ControlApi.dto;

import com.order.service.ControlApi.domain.PaymentMethod;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Endereço é obrigatório")
    private String endereço;

    @NotNull(message = "Forma de pagamento é obrigatória")
    private PaymentMethod formaPagamento;

    @NotEmpty(message = "A lista de produtos não pode estar vazia")
    private List<OrderItemRequestDTO> produtos;

    // Manual Builder
    public static OrderRequestDTOBuilder builder() {
        return new OrderRequestDTOBuilder();
    }

    public static class OrderRequestDTOBuilder {
        private String nome;
        private String email;
        private String endereço;
        private PaymentMethod formaPagamento;
        private List<OrderItemRequestDTO> produtos;

        public OrderRequestDTOBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public OrderRequestDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public OrderRequestDTOBuilder endereço(String endereço) {
            this.endereço = endereço;
            return this;
        }

        public OrderRequestDTOBuilder formaPagamento(PaymentMethod formaPagamento) {
            this.formaPagamento = formaPagamento;
            return this;
        }

        public OrderRequestDTOBuilder produtos(List<OrderItemRequestDTO> produtos) {
            this.produtos = produtos;
            return this;
        }

        public OrderRequestDTO build() {
            OrderRequestDTO dto = new OrderRequestDTO();
            dto.setNome(this.nome);
            dto.setEmail(this.email);
            dto.setEndereço(this.endereço);
            dto.setFormaPagamento(this.formaPagamento);
            dto.setProdutos(this.produtos);
            return dto;
        }
    }
}
