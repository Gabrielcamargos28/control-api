import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { OrderService } from './core/services/order.service';
import { OrderSuccessComponent } from './features/orders/order-success/order-success.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    OrderSuccessComponent
  ],
  template: `
    <div class="app-layout">
      <!-- Sidebar / Menu -->
      <aside class="sidebar">
        <div class="brand">
          <h1>ControlApi</h1>
        </div>

        <nav class="nav-menu">
          <a routerLink="/produtos" routerLinkActive="active" class="nav-item">
            <span class="icon"></span> Produtos
          </a>
          <a routerLink="/venda" routerLinkActive="active" class="nav-item">
            <span class="icon"></span> Venda (Carrinho)
          </a>
          <a routerLink="/pedidos" routerLinkActive="active" class="nav-item">
            <span class="icon"></span> Meus Pedidos
          </a>
        </nav>
      </aside>

      <!-- Main Content -->
      <main class="main-content">
        <app-order-success *ngIf="orderService.lastOrder()" />

        <div *ngIf="!orderService.lastOrder()">
          <router-outlet />
        </div>
      </main>
    </div>
  `,
  styles: [`
    .app-layout {
      display: flex;
      min-height: 100vh;
      background: #f1f3f5;
    }

    /* Sidebar Styles */
    .sidebar {
      width: 260px;
      background: #2d3436;
      color: white;
      padding: 2rem 1rem;
      display: flex;
      flex-direction: column;
      position: sticky;
      top: 0;
      height: 100vh;
    }

    .brand { margin-bottom: 3rem; text-align: center; }
    .brand h1 { font-size: 1.5rem; color: #55efc4; margin: 0; }

    .nav-menu { display: flex; flex-direction: column; gap: 0.5rem; }

    .nav-item {
      display: flex;
      align-items: center;
      padding: 1rem 1.5rem;
      color: #b2bec3;
      text-decoration: none;
      border-radius: 10px;
      font-weight: 500;
      transition: all 0.2s;
    }

    .nav-item .icon { margin-right: 12px; font-size: 1.2rem; }

    .nav-item:hover {
      background: #444b4d;
      color: white;
    }

    .nav-item.active {
      background: #0984e3;
      color: white;
      box-shadow: 0 4px 12px rgba(9, 132, 227, 0.3);
    }

    /* Main Content Styles */
    .main-content {
      flex: 1;
      padding: 2rem;
      overflow-y: auto;
    }

    @media (max-width: 768px) {
      .app-layout { flex-direction: column; }
      .sidebar { width: 100%; height: auto; position: static; padding: 1rem; }
      .nav-menu { flex-direction: row; flex-wrap: wrap; justify-content: center; }
    }
  `]
})
export class AppComponent {
  orderService = inject(OrderService);
}
