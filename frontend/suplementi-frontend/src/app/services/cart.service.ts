import { Injectable } from '@angular/core';
import { Product } from '../models/product.model';

export interface CartLine {
  product: Product;
  quantity: number;
}

@Injectable({ providedIn: 'root' })
export class CartService {
  private lines: CartLine[] = [];

  constructor() {
    const saved = sessionStorage.getItem('cart');
    if (saved) this.lines = JSON.parse(saved);
  }

  getItems(): CartLine[] {
    return this.lines;
  }

  getItemCount(): number {
    return this.lines.reduce((sum, l) => sum + l.quantity, 0);
  }

  getTotal(): number {
    return this.lines.reduce((sum, l) => sum + l.product.price * l.quantity, 0);
  }

  add(product: Product, quantity: number = 1): void {
    const existing = this.lines.find((l) => l.product.id === product.id);
    if (existing) {
      existing.quantity += quantity;
    } else {
      this.lines.push({ product, quantity });
    }
    this.save();
  }

  updateQuantity(productId: number, quantity: number): void {
    if (quantity <= 0) {
      this.remove(productId);
      return;
    }
    const line = this.lines.find((l) => l.product.id === productId);
    if (line) line.quantity = quantity;
    this.save();
  }

  remove(productId: number): void {
    this.lines = this.lines.filter((l) => l.product.id !== productId);
    this.save();
  }

  clear(): void {
    this.lines = [];
    sessionStorage.removeItem('cart');
  }

  private save(): void {
    sessionStorage.setItem('cart', JSON.stringify(this.lines));
  }
}
