import { Component, inject, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../../../core/services/order.service';

@Component({
  selector: 'app-order-success',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div *ngIf="orderService.lastOrder() as order" class="success-view">
      <div class="card">
        <h2>Pedido Criado com Sucesso!</h2>

        <div class="order-info-grid">
          <p>ID do Pedido: <strong>#{{ order.id }}</strong></p>
          <p>Cliente: <strong>{{ order.nome }}</strong></p>
          <p>E-mail: <strong>{{ order.email }}</strong></p>
          <p>Endereço: <strong>{{ order.endereco }}</strong></p>
          <p>Pagamento: <span class="badge">{{ order.formaPagamento }}</span></p>
        </div>

        <table class="details-table">
          <thead>
            <tr>
              <th>Produto</th>
              <th>Qtd</th>
              <th>Preço Un.</th>
              <th>Subtotal</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of order.itens">
              <td>{{ item.nomeProduto }}</td>
              <td>{{ item.quantidade }}</td>
              <td>{{ item.preco | currency:'BRL' }}</td>
              <td>{{ (item.preco * item.quantidade) | currency:'BRL' }}</td>
            </tr>
          </tbody>
        </table>

        <div class="total-section">
          <h3>Total: {{ calculateTotal(order) | currency:'BRL' }}</h3>
        </div>

        <button class="btn-primary" (click)="orderService.clearOrder()">Novo Pedido</button>
      </div>
    </div>
  `,
  styles: [`
    .success-view { max-width: 800px; margin: 2rem auto; }
    .card { background: white; padding: 2rem; border-radius: 12px; box-shadow: 0 4px 10px rgba(0,0,0,0.05); }
    .order-info-grid {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 1rem;
      margin: 1.5rem 0;
      padding: 1rem;
      background: #f8f9fa;
      border-radius: 8px;
    }
    .order-info-grid p { margin: 0; font-size: 0.95rem; }
    .badge { background: #55efc4; color: #00b894; padding: 0.2rem 0.6rem; border-radius: 20px; font-weight: bold; font-size: 0.8rem; }
    .details-table { width: 100%; border-collapse: collapse; margin: 2rem 0; }
    .details-table th { text-align: left; color: #636e72; border-bottom: 2px solid #eee; padding: 0.8rem 0; }
    .details-table td { padding: 0.8rem 0; border-bottom: 1px solid #eee; }
    .total-section { text-align: right; margin: 1rem 0; }
    .total-section h3 { font-size: 1.6rem; color: #2d3436; }
    .btn-primary { width: 100%; padding: 1rem; background: #0984e3; color: white; border: none; border-radius: 10px; font-size: 1.1rem; font-weight: bold; cursor: pointer; transition: background 0.2s; }
    .btn-primary:hover { background: #074b83; }
  `]
})
export class OrderSuccessComponent {
  orderService = inject(OrderService);

  calculateTotal(order: any): number {
    return order.itens.reduce((acc: number, item: any) => acc + (item.preco * item.quantidade), 0);
  }
}
