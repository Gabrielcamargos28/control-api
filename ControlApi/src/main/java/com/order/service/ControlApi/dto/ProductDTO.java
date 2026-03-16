package com.order.service.ControlApi.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String nome;
    private String descrição;
    private Double preço;
    private Integer estoque;
    private String imagem;

    // Manual Builder
    public static ProductDTOBuilder builder() {
        return new ProductDTOBuilder();
    }

    public static class ProductDTOBuilder {
        private Long id;
        private String nome;
        private String descrição;
        private Double preço;
        private Integer estoque;
        private String imagem;

        public ProductDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProductDTOBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public ProductDTOBuilder descrição(String descrição) {
            this.descrição = descrição;
            return this;
        }

        public ProductDTOBuilder preço(Double preço) {
            this.preço = preço;
            return this;
        }

        public ProductDTOBuilder estoque(Integer estoque) {
            this.estoque = estoque;
            return this;
        }

        public ProductDTOBuilder imagem(String imagem) {
            this.imagem = imagem;
            return this;
        }

        public ProductDTO build() {
            ProductDTO dto = new ProductDTO();
            dto.setId(this.id);
            dto.setNome(this.nome);
            dto.setDescrição(this.descrição);
            dto.setPreço(this.preço);
            dto.setEstoque(this.estoque);
            dto.setImagem(this.imagem);
            return dto;
        }
    }
}
