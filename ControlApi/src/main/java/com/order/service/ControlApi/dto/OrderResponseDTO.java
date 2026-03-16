package com.order.service.ControlApi.dto;

import com.order.service.ControlApi.domain.PaymentMethod;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String endereço;
    private PaymentMethod formaPagamento;
    private List<OrderItemResponseDTO> itens;

    // Manual Builder for OrderResponseDTO
    public static OrderResponseDTOBuilder builder() {
        return new OrderResponseDTOBuilder();
    }

    public static class OrderResponseDTOBuilder {
        private Long id;
        private String nome;
        private String email;
        private String endereço;
        private PaymentMethod formaPagamento;
        private List<OrderItemResponseDTO> itens;

        public OrderResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderResponseDTOBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public OrderResponseDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public OrderResponseDTOBuilder endereço(String endereço) {
            this.endereço = endereço;
            return this;
        }

        public OrderResponseDTOBuilder formaPagamento(PaymentMethod formaPagamento) {
            this.formaPagamento = formaPagamento;
            return this;
        }

        public OrderResponseDTOBuilder itens(List<OrderItemResponseDTO> itens) {
            this.itens = itens;
            return this;
        }

        public OrderResponseDTO build() {
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setId(this.id);
            dto.setNome(this.nome);
            dto.setEmail(this.email);
            dto.setEndereço(this.endereço);
            dto.setFormaPagamento(this.formaPagamento);
            dto.setItens(this.itens);
            return dto;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponseDTO {
        private Long produtoId;
        private String nomeProduto;
        private Double preço;
        private Integer quantidade;

        // Manual Builder for OrderItemResponseDTO
        public static OrderItemResponseDTOBuilder builder() {
            return new OrderItemResponseDTOBuilder();
        }

        public static class OrderItemResponseDTOBuilder {
            private Long produtoId;
            private String nomeProduto;
            private Double preço;
            private Integer quantidade;

            public OrderItemResponseDTOBuilder produtoId(Long produtoId) {
                this.produtoId = produtoId;
                return this;
            }

            public OrderItemResponseDTOBuilder nomeProduto(String nomeProduto) {
                this.nomeProduto = nomeProduto;
                return this;
            }

            public OrderItemResponseDTOBuilder preço(Double preço) {
                this.preço = preço;
                return this;
            }

            public OrderItemResponseDTOBuilder quantidade(Integer quantidade) {
                this.quantidade = quantidade;
                return this;
            }

            public OrderItemResponseDTO build() {
                OrderItemResponseDTO dto = new OrderItemResponseDTO();
                dto.setProdutoId(this.produtoId);
                dto.setNomeProduto(this.nomeProduto);
                dto.setPreço(this.preço);
                dto.setQuantidade(this.quantidade);
                return dto;
            }
        }
    }
}
