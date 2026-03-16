import { Injectable, computed, signal } from '@angular/core';
import { ProductDTO } from '../models/product.dto';

export interface CartItem {
  product: ProductDTO;
  quantity: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  items = signal<CartItem[]>([]);
  customerName = signal<string>('');
  customerEmail = signal<string>('');
  customerAddress = signal<string>('');
  paymentMethod = signal<'CARD' | 'BILL' | 'PIX'>('CARD');

  total = computed(() => {
    return this.items().reduce((acc, item) => acc + (item.product.preco * item.quantity), 0);
  });

  count = computed(() => {
    return this.items().reduce((acc, item) => acc + item.quantity, 0);
  });

  addToCart(product: ProductDTO) {
    this.items.update(prevItems => {
      const existing = prevItems.find(i => i.product.id === product.id);
      if (existing) {
        return prevItems.map(i => i.product.id === product.id ? { ...i, quantity: i.quantity + 1 } : i);
      }
      return [...prevItems, { product, quantity: 1 }];
    });
  }

  removeFromCart(productId: number) {
    this.items.update(prevItems => prevItems.filter(i => i.product.id !== productId));
  }

  updateQuantity(productId: number, quantity: number) {
    if (quantity <= 0) {
      this.removeFromCart(productId);
      return;
    }
    this.items.update(prevItems => prevItems.map(i => i.product.id === productId ? { ...i, quantity } : i));
  }

  clearCart() {
    this.items.set([]);
    this.customerName.set('');
    this.customerEmail.set('');
    this.customerAddress.set('');
    this.paymentMethod.set('CARD');
  }
}
