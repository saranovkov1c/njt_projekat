import { Order } from './order.model';
import { Product } from './product.model';

export class OrderItem {
  public id!: number;
  public quantity!: number;
  public priceAtPurchase!: number;
  public order!: Order;
  public product!: Product;
}
