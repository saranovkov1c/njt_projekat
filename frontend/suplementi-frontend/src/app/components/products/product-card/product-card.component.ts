import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Product } from '../../../models/product.model';
import { ProductService } from '../../../services/product.service';
import { UserService } from '../../../services/user.service';
import { ClientService } from '../../../services/client.service';
import { CartService } from '../../../services/cart.service';

@Component({
  selector: 'app-product-card',
  standalone: false,
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent {
  @Input() product!: Product;
  @Output() removedProduct = new EventEmitter<Product>();

  edit: boolean = false;

  constructor(
    public userService: UserService,
    public clientService: ClientService,
    private cartService: CartService,
    private productService: ProductService
  ) {}

  addToCart() {
    this.cartService.add(this.product, 1);
    alert(`"${this.product.name}" dodat u korpu.`);
  }

  update() {
    this.edit = true;
  }

  onSave(updated: Product) {
    this.productService.updateProduct(updated).subscribe({
      next: () => {
        this.product = updated;
        this.edit = false;
        alert('Proizvod uspešno ažuriran!');
      },
      error: (err) => {
        console.error(err);
        alert('Ažuriranje nije uspelo!');
      }
    });
  }

  delete() {
    if (!confirm('Obrisati ovaj proizvod?')) return;
    this.productService.delete(this.product.id).subscribe({
      next: () => {
        alert('Proizvod obrisan!');
        this.removedProduct.emit(this.product);
      },
      error: (err) => {
        console.error(err);
        alert('Brisanje nije uspelo!');
      }
    });
  }
}
