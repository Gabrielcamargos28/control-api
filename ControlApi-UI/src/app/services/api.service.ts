import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductDTO } from '../core/models/product.dto';
import { OrderRequest, OrderResponse } from '../core/models/order.dto';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private http = inject(HttpClient);
  private apiUrl = '';

  getProducts(): Observable<ProductDTO[]> {
    return this.http.get<ProductDTO[]>(`${this.apiUrl}/products`);
  }

  createOrder(order: OrderRequest): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}/orders`, order);
  }

  getOrder(id: number): Observable<OrderResponse> {
    return this.http.get<OrderResponse>(`${this.apiUrl}/orders/${id}`);
  }
}
