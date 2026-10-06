import { Category } from './category.model';

export class Product {
  public id!: number;
  public name!: string;
  public description!: string;
  public price!: number;
  public stock!: number;
  public form!: string;      // POWDER, CAPSULE, TABLET, LIQUID, BAR, GUMMY
  public imageUrl!: string;
  public category!: Category;
}
