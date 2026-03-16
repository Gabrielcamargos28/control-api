import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../../core/services/product.service';
import { CartService } from '../../../core/services/cart.service';
import { ProductDTO } from '../../../core/models/product.dto';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <section class="products-section">
      <h2>Catálogo de Produtos</h2>
      <div *ngIf="loading()" class="loader">Carregando produtos...</div>
      <div *ngIf="error()" class="error-banner">{{ error() }}</div>
      
      <div *ngIf="!loading() && products().length === 0" class="empty-state">
        Nenhum produto disponível.
      </div>
      
      <div class="grid">
        <div *ngFor="let product of products()" class="product-card">
          <img [src]="product.imagem" [alt]="product.nome" class="product-image">
          <div class="product-info">
            <h3>{{ product.nome }}</h3>
            <p class="description">{{ product.descricao }}</p>
            <p class="price">{{ product.preco | currency:'BRL' }}</p>
            <p class="stock">Estoque: {{ product.estoque }}</p>
            <button class="btn-add" (click)="addToCart(product)">+ Adicionar</button>
          </div>
        </div>
      </div>
    </section>
  `,
  styles: [`
    .products-section { margin-bottom: 2rem; }
    .grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
      gap: 1.5rem;
    }
    .product-card {
      background: white;
      border-radius: 12px;
      box-shadow: 0 4px 6px rgba(0,0,0,0.05);
      transition: transform 0.2s;
      border: 1px solid #eee;
      overflow: hidden;
      display: flex;
      flex-direction: column;
    }
    .product-card:hover { transform: translateY(-5px); }
    .product-image {
      width: 100%;
      height: 200px;
      object-fit: contain;
      background: #f8f9fa;
      padding: 1rem;
    }
    .product-info {
      padding: 1.5rem;
      display: flex;
      flex-direction: column;
      flex-grow: 1;
    }
    .product-info h3 { 
      margin: 0 0 0.5rem; 
      color: #2d3436; 
      font-size: 1.1rem;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }
    .description {
      font-size: 0.9rem;
      color: #636e72;
      margin-bottom: 1rem;
      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
      overflow: hidden;
      line-height: 1.4;
    }
    .price {
      font-size: 1.25rem;
      font-weight: bold;
      color: #00b894;
      margin-bottom: 0.5rem;
    }
    .stock {
      font-size: 0.85rem;
      color: #b2bec3;
      margin-bottom: 1rem;
    }
    .btn-add {
      margin-top: auto;
      width: 100%;
      background: #0984e3;
      color: white;
      border: none;
      padding: 0.75rem;
      border-radius: 8px;
      cursor: pointer;
      font-weight: 600;
    }
    .btn-add:hover { background: #074b83; }
  `]
})
export class ProductListComponent implements OnInit {
  private productService = inject(ProductService);
  private cartService = inject(CartService);

  products = signal<ProductDTO[]>([]);
  loading = signal(false);
  error = signal<string | null>(null);

  ngOnInit() {
    this.fetchProducts();
  }

  fetchProducts() {
    this.loading.set(true);
    this.productService.getProducts().subscribe({
      next: (data) => {
        this.products.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Erro ao carregar produtos.');
        this.loading.set(false);
      }
    });
  }

  addToCart(product: ProductDTO) {
    this.cartService.addToCart(product);
  }
}
