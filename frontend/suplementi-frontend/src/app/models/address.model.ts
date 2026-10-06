export class Address {
  public id!: number;
  public street!: string;
  public number!: string;
  public city!: string;
  public postal_code!: number;
  public client!: { id: number };
}