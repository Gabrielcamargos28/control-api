import { Component, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CartService } from '../../../core/services/cart.service';
import { OrderService } from '../../../core/services/order.service';
import { OrderRequest } from '../../../core/models/order.dto';
import { ProductListComponent } from '../../products/product-list/product-list.component';

@Component({
  selector: 'app-shopping-cart',
  standalone: true,
  imports: [CommonModule, FormsModule, ProductListComponent],
  template: `
    <div class="venda-container">
      <!-- Catalog on the left -->
      <div class="catalog-side">
        <app-product-list />
      </div>

      <!-- Cart on the right -->
      <aside class="cart-side">
        <div class="card cart-card">
          <h2>Seu Carrinho</h2>

          <div class="form-group">
            <label for="customerName">Nome Completo</label>
            <input
              id="customerName"
              type="text"
              [ngModel]="cartService.customerName()"
              (ngModelChange)="cartService.customerName.set($event)"
              placeholder="Ex: João Silva"
            >
          </div>

          <div class="form-group">
            <label for="customerEmail">E-mail</label>
            <input
              id="customerEmail"
              type="email"
              [ngModel]="cartService.customerEmail()"
              (ngModelChange)="cartService.customerEmail.set($event)"
              placeholder="joao@exemplo.com"
            >
          </div>

          <div class="form-group">
            <label for="customerAddress">Endereço de Entrega</label>
            <input
              id="customerAddress"
              type="text"
              [ngModel]="cartService.customerAddress()"
              (ngModelChange)="cartService.customerAddress.set($event)"
              placeholder="Rua, Número, Bairro, Cidade"
            >
          </div>

          <div class="form-group">
            <label for="payment">Método de Pagamento</label>
            <select id="payment" [ngModel]="cartService.paymentMethod()" (ngModelChange)="cartService.paymentMethod.set($event)">
              <option value="CARD">Cartão (CARD)</option>
              <option value="BILL">Boleto (BILL)</option>
              <option value="PIX">PIX</option>
            </select>
          </div>

          <hr>

          <div *ngIf="cartService.items().length === 0" class="empty-cart">
            Carrinho vazio. Adicione produtos.
          </div>

          <ul class="cart-list">
            <li *ngFor="let item of cartService.items()" class="cart-item">
              <div class="item-info">
                <span class="item-name">{{ item.product.nome }}</span>
                <div class="qty-controls">
                  <button (click)="cartService.updateQuantity(item.product.id, item.quantity - 1)">-</button>
                  <span>{{ item.quantity }}</span>
                  <button (click)="cartService.updateQuantity(item.product.id, item.quantity + 1)">+</button>
                </div>
              </div>
              <button class="btn-remove" (click)="cartService.removeFromCart(item.product.id)">Remover</button>
            </li>
          </ul>

          <div class="cart-footer" *ngIf="cartService.items().length > 0">
            <div class="total-row">
              <span>Total:</span>
              <strong>{{ cartService.total() | currency:'BRL' }}</strong>
            </div>
            <button
              class="btn-checkout"
              [disabled]="!isValidOrder() || loading()"
              (click)="placeOrder()"
            >
              {{ loading() ? 'Processando...' : 'Finalizar Pedido' }}
            </button>
          </div>

          <div *ngIf="error()" class="error-banner">{{ error() }}</div>
        </div>
      </aside>
    </div>
  `,
  styles: [`
    .venda-container {
      display: grid;
      grid-template-columns: 1fr 380px;
      gap: 2rem;
      align-items: start;
    }

    .catalog-side { min-width: 0; }

    .cart-side {
      position: sticky;
      top: 2rem;
    }

    .card { background: white; padding: 1.5rem; border-radius: 12px; box-shadow: 0 4px 10px rgba(0,0,0,0.05); }
    .form-group { margin-bottom: 1rem; display: flex; flex-direction: column; gap: 0.25rem; }
    .form-group label { font-weight: 600; color: #636e72; font-size: 0.85rem; }
    input, select { padding: 0.5rem; border: 1.5px solid #dfe6e9; border-radius: 8px; font-size: 0.9rem; }

    .cart-list { list-style: none; padding: 0; margin: 1rem 0; max-height: 300px; overflow-y: auto; }
    .cart-item { display: flex; justify-content: space-between; align-items: center; padding: 0.6rem 0; border-bottom: 1px solid #eee; }
    .item-info { display: flex; flex-direction: column; gap: 0.25rem; }
    .item-name { font-weight: 600; font-size: 0.9rem; }
    .qty-controls { display: flex; align-items: center; gap: 0.6rem; }
    .qty-controls button { width: 22px; height: 22px; border-radius: 50%; border: 1px solid #dfe6e9; background: white; cursor: pointer; display: flex; align-items: center; justify-content: center; }
    .btn-remove { color: #d63031; background: none; border: none; cursor: pointer; font-weight: 600; font-size: 0.8rem; }

    .cart-footer { margin-top: 1rem; border-top: 2px solid #eee; padding-top: 1rem; }
    .total-row { display: flex; justify-content: space-between; font-size: 1.2rem; margin-bottom: 1rem; }
    .btn-checkout { width: 100%; padding: 0.8rem; background: #00b894; color: white; border: none; border-radius: 10px; font-size: 1rem; font-weight: bold; cursor: pointer; }
    .btn-checkout:disabled { background: #b2bec3; cursor: not-allowed; }
    .error-banner { color: #d63031; background: #fab1a033; padding: 0.75rem; border-radius: 8px; margin-top: 1rem; text-align: center; font-size: 0.9rem; }

    .empty-cart { text-align: center; color: #b2bec3; padding: 2rem 0; }

    @media (max-width: 1200px) {
      .venda-container { grid-template-columns: 1fr; }
      .cart-side { position: static; }
    }
  `]
})
export class ShoppingCartComponent {
  cartService = inject(CartService);
  private orderService = inject(OrderService);

  loading = signal(false);
  error = signal<string | null>(null);

  isValidOrder = computed(() => {
    return (
      this.cartService.customerName().trim().length > 2 &&
      this.cartService.customerEmail().includes('@') &&
      this.cartService.customerAddress().trim().length > 5 &&
      this.cartService.items().length > 0
    );
  });

  placeOrder() {
    if (!this.isValidOrder()) return;

    const request: OrderRequest = {
      nome: this.cartService.customerName(),
      email: this.cartService.customerEmail(),
      endereço: this.cartService.customerAddress(),
      formaPagamento: this.cartService.paymentMethod(),
      produtos: this.cartService.items().map(i => ({
        idProduto: i.product.id,
        quantidade: i.quantity
      }))
    };

    this.loading.set(true);
    this.orderService.createOrder(request).subscribe({
      next: (orderId) => {
        this.fetchOrderDetails(orderId);
      },
      error: (err) => {
        console.error('Erro na criação do pedido:', err);
        this.error.set('Erro ao criar pedido.');
        this.loading.set(false);
      }
    });
  }

  fetchOrderDetails(id: number) {
    this.orderService.getOrder(id).subscribe({
      next: () => {
        this.cartService.clearCart();
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Pedido criado, mas erro ao buscar detalhes.');
        this.loading.set(false);
      }
    });
  }
}
