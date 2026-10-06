import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from '../../../services/cart.service';
import { ClientService } from '../../../services/client.service';
import { OrderService } from '../../../services/order.service';
import { OrderItemService } from '../../../services/order-item.service';
import { AddressService } from '../../../services/address.service';

@Component({
  selector: 'app-cart',
  standalone: false,
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  public submitting = false;

  constructor(
    public cartService: CartService,
    private clientService: ClientService,
    private orderService: OrderService,
    private orderItemService: OrderItemService,
    private addressService: AddressService,
    private router: Router
  ) { }

  increase(productId: number, qty: number) {
    this.cartService.updateQuantity(productId, qty + 1);
  }
  decrease(productId: number, qty: number) {
    this.cartService.updateQuantity(productId, qty - 1);
  }
  remove(productId: number) {
    this.cartService.remove(productId);
  }

  placeOrder() {
    const client = this.clientService.getSpecificClient();
    if (!client) {
      alert('Morate biti prijavljeni kao klijent da biste naručili.');
      return;
    }
    if (this.cartService.getItems().length === 0) {
      alert('Korpa je prazna.');
      return;
    }

    // Provera zaliha: ima li dovoljno svakog proizvoda na stanju
    const nedovoljno = this.cartService.getItems().find(
      line => line.quantity > line.product.stock
    );
    if (nedovoljno) {
      alert(`Nema dovoljno na stanju za "${nedovoljno.product.name}". Dostupno: ${nedovoljno.product.stock}, u korpi: ${nedovoljno.quantity}.`);
      return;
    }

    // Provera: klijent mora imati bar jednu adresu za isporuku
    this.addressService.getByClient(client.id).subscribe({
      next: (res) => {
        const addresses = res.data.values as any[];
        if (!addresses || addresses.length === 0) {
          alert('Morate dodati adresu za isporuku pre naručivanja. Idite na "Moje adrese".');
          this.router.navigate(['/my-addresses']);
          return;
        }
        this.createOrder(client);
      },
      error: () => {
        alert('Ne mogu da proverim vaše adrese. Pokušajte ponovo.');
      }
    });
  }

  private createOrder(client: any) {
    this.submitting = true;
    const today = new Date().toISOString().substring(0, 10);

    const order: any = {
      ordered: today,
      arrival: today,
      client: { id: client.id },
      user: null
    };

    this.orderService.createOrder(order).subscribe({
      next: (res) => {
        const createdOrder = res.data.value;   // porudzbina sa dodeljenim ID-jem
        this.addItems(createdOrder.id);
      },
      error: (err) => {
        this.submitting = false;
        console.error(err);
        alert('Greška pri kreiranju porudžbine.');
      }
    });
  }

  private addItems(orderId: number) {
    const lines = this.cartService.getItems();
    let done = 0;

    lines.forEach((line) => {
      const item = {
        quantity: line.quantity,
        order: { id: orderId },
        product: { id: line.product.id }
      };
      this.orderItemService.add(item).subscribe({
        next: (res) => {
          const msg = res.message ?? res.data?.value ?? '';
          if (msg.includes('Nema dovoljno')) {
            this.submitting = false;
            alert(msg);   // "Nema dovoljno na stanju za..."
            return;
          }
          done++;
          if (done === lines.length) {
            this.submitting = false;
            this.cartService.clear();
            alert('Porudžbina uspešno kreirana!');
            this.router.navigate(['/my-orders']);
          }
        },
        error: (err) => {
          this.submitting = false;
          console.error(err);
          alert('Greška pri dodavanju stavke.');
        }
      });
    });
  }
}
