import { Routes } from '@angular/router';
import { ProductListComponent } from './features/products/product-list/product-list.component';
import { ShoppingCartComponent } from './features/cart/shopping-cart/shopping-cart.component';
import { OrderListComponent } from './features/orders/order-list/order-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'venda', pathMatch: 'full' },
  { path: 'produtos', component: ProductListComponent },
  { path: 'venda', component: ShoppingCartComponent },
  { path: 'pedidos', component: OrderListComponent },
  { path: '**', redirectTo: 'venda' }
];
