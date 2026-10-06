import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../../services/order.service';
import { OrderItemService } from '../../../services/order-item.service';
import { Order } from '../../../models/order.model';

@Component({
  selector: 'app-order-list',
  standalone: false,
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {
  public orders: Order[] = [];
  public expandedOrderId: number | null = null;
  public items: any[] = [];

  constructor(
    private orderService: OrderService,
    private orderItemService: OrderItemService
  ) {}

  ngOnInit() {
    this.orderService.getAll().subscribe({
      next: (res) => (this.orders = res.data.values as Order[]),
      error: (err) => console.error(err)
    });
  }

  toggleItems(order: Order) {
    if (this.expandedOrderId === order.id) {
      this.expandedOrderId = null;
      return;
    }
    this.expandedOrderId = order.id;
    this.orderItemService.getByOrder(order.id).subscribe({
      next: (res) => (this.items = res.data.values),
      error: (err) => console.error(err)
    });
  }

  delete(order: Order) {
    if (!confirm('Obrisati ovu porudžbinu?')) return;
    this.orderService.delete(order.id).subscribe({
      next: () => {
        alert('Porudžbina obrisana!');
        this.orders = this.orders.filter((o) => o.id !== order.id);
      },
      error: (err) => { console.error(err); alert('Brisanje nije uspelo (možda ima stavke).'); }
    });
  }

  orderTotal(): number {
    return this.items.reduce((s, i) => s + i.priceAtPurchase * i.quantity, 0);
  }
}
