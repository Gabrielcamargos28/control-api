import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { ProductDTO } from '../models/product.dto';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private http = inject(HttpClient);
  private apiUrl = '/products';

  getProducts(): Observable<ProductDTO[]> {
    return this.http.get<any[]>(this.apiUrl).pipe(
      map(products => products.map(p => ({
        id: p.id,
        nome: p.nome,
        descricao: p.descrição, // Mapeia 'descrição' (API) para 'descricao' (Código)
        preco: p.preço,         // Mapeia 'preço' (API) para 'preco' (Código)
        estoque: p.estoque,
        imagem: p.imagem
      })))
    );
  }
}
