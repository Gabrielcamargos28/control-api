export interface OrderItemRequest {
  idProduto: number;
  quantidade: number;
}

export interface OrderRequest {
  nome: string;
  email: string;
  endereço: string;
  formaPagamento: 'CARD' | 'BILL' | 'PIX';
  produtos: OrderItemRequest[];
}

export interface OrderItemResponse {
  produtoId: number;
  nomeProduto: string;
  preco: number; // Mapeado de preço
  quantidade: number;
}

export interface OrderResponse {
  id: number;
  nome: string;
  email: string;
  endereco: string; // Mapeado de endereço
  formaPagamento: 'CARD' | 'BILL' | 'PIX';
  itens: OrderItemResponse[];
}
