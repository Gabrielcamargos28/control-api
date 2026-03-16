import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap, map } from 'rxjs';
import { OrderRequest, OrderResponse } from '../models/order.dto';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private http = inject(HttpClient);
  private apiUrl = '/orders';

  lastOrder = signal<OrderResponse | null>(null);

  createOrder(order: OrderRequest): Observable<number> {
    return this.http.post<number>(this.apiUrl, order);
  }

  getAllOrders(): Observable<OrderResponse[]> {
    return this.http.get<any[]>(this.apiUrl).pipe(
      map(orders => orders.map(o => this.mapToOrderResponse(o)))
    );
  }

  getOrder(id: number): Observable<OrderResponse> {
    return this.http.get<any>(`${this.apiUrl}/${id}`).pipe(
      map(o => this.mapToOrderResponse(o)),
      tap(order => this.lastOrder.set(order))
    );
  }

  private mapToOrderResponse(o: any): OrderResponse {
    return {
      id: o.id,
      nome: o.nome,
      email: o.email,
      endereco: o.endereço,
      formaPagamento: o.formaPagamento,
      itens: o.itens.map((i: any) => ({
        produtoId: i.produtoId,
        nomeProduto: i.nomeProduto,
        preco: i.preço,
        quantidade: i.quantidade
      }))
    };
  }

  clearOrder() {
    this.lastOrder.set(null);
  }
}
