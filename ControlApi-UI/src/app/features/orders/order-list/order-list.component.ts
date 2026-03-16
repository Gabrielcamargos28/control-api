import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../../../core/services/order.service';
import { OrderResponse } from '../../../core/models/order.dto';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <section class="orders-section">
      <div class="header-row">
        <h2>Histórico de Pedidos </h2>
        <button class="btn-refresh" (click)="fetchOrders()"> Atualizar</button>
      </div>

      <div *ngIf="loading()" class="loader">Carregando pedidos...</div>
      <div *ngIf="error()" class="error-banner">{{ error() }}</div>

      <div *ngIf="!loading() && orders().length === 0" class="empty-state">
        Nenhum pedido encontrado.
      </div>

      <div class="orders-grid" *ngIf="!loading() && orders().length > 0">
        <div *ngFor="let order of orders()" class="order-card">
          <div class="order-header">
            <span class="order-id">#{{ order.id }}</span>
            <span class="badge">{{ order.formaPagamento }}</span>
          </div>
          <div class="order-body">
            <p><strong>Cliente:</strong> {{ order.nome }}</p>
            <p><strong>Itens:</strong> {{ order.itens.length }} produto(s)</p>
            <p class="order-total">
              <strong>Total:</strong> {{ calculateTotal(order) | currency:'BRL' }}
            </p>
          </div>
          <button class="btn-detail" (click)="viewDetail(order)">Ver Detalhes</button>
        </div>
      </div>
    </section>
  `,
  styles: [`
    .orders-section { padding: 1rem 0; }
    .header-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem; }
    .btn-refresh { background: #eee; border: none; padding: 0.5rem 1rem; border-radius: 8px; cursor: pointer; }

    .orders-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
      gap: 1.5rem;
    }

    .order-card {
      background: white;
      border-radius: 12px;
      padding: 1.5rem;
      box-shadow: 0 4px 6px rgba(0,0,0,0.05);
      border: 1px solid #eee;
      display: flex;
      flex-direction: column;
    }

    .order-header {
      display: flex;
      justify-content: space-between;
      margin-bottom: 1rem;
      border-bottom: 1px solid #f1f1f1;
      padding-bottom: 0.5rem;
    }

    .order-id { font-weight: bold; color: #0984e3; }
    .badge { background: #e1f5fe; color: #0288d1; padding: 0.2rem 0.6rem; border-radius: 12px; font-size: 0.75rem; font-weight: bold; }

    .order-body p { margin: 0.4rem 0; font-size: 0.95rem; color: #636e72; }
    .order-total { color: #2d3436; font-size: 1.1rem !important; margin-top: 0.8rem !important; }

    .btn-detail {
      margin-top: 1.5rem;
      background: #f8f9fa;
      border: 1px solid #dfe6e9;
      padding: 0.6rem;
      border-radius: 8px;
      cursor: pointer;
      font-weight: 600;
      transition: all 0.2s;
    }
    .btn-detail:hover { background: #0984e3; color: white; border-color: #0984e3; }

    .loader, .empty-state { text-align: center; padding: 3rem; color: #b2bec3; }
    .error-banner { background: #ff767522; color: #d63031; padding: 1rem; border-radius: 8px; text-align: center; }
  `]
})
export class OrderListComponent implements OnInit {
  private orderService = inject(OrderService);

  orders = signal<OrderResponse[]>([]);
  loading = signal(false);
  error = signal<string | null>(null);

  ngOnInit() {
    this.fetchOrders();
  }

  fetchOrders() {
    this.loading.set(true);
    this.orderService.getAllOrders().subscribe({
      next: (data) => {
        this.orders.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error(err);
        this.error.set('Erro ao carregar histórico de pedidos.');
        this.loading.set(false);
      }
    });
  }

  calculateTotal(order: OrderResponse): number {
    return order.itens.reduce((acc, item) => acc + (item.preco * item.quantidade), 0);
  }

  viewDetail(order: OrderResponse) {
    this.orderService.lastOrder.set(order);
  }
}
