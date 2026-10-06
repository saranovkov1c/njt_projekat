import { Client } from './client.model';
import { User } from './user.model';

export class Order {
  public id!: number;
  public ordered!: string;   // "yyyy-MM-dd"
  public arrival!: string;
  public client!: Client;
  public user!: User | null;
}
