import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../../services/order.service';
import { OrderItemService } from '../../../services/order-item.service';
import { ClientService } from '../../../services/client.service';
import { Order } from '../../../models/order.model';

@Component({
  selector: 'app-my-orders',
  standalone: false,
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {
  public orders: Order[] = [];
  public expandedOrderId: number | null = null;
  public items: any[] = [];

  constructor(
    private orderService: OrderService,
    private orderItemService: OrderItemService,
    private clientService: ClientService
  ) {}

  ngOnInit() {
    const client = this.clientService.getSpecificClient();
    if (!client) return;

    this.orderService.getClientsOrders(client).subscribe({
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

  orderTotal(): number {
    return this.items.reduce((s, i) => s + i.priceAtPurchase * i.quantity, 0);
  }
}
