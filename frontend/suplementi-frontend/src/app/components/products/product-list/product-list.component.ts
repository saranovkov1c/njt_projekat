import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../../services/product.service';
import { CategoryService } from '../../../services/category.service';
import { UserService } from '../../../services/user.service';
import { Product } from '../../../models/product.model';
import { Category } from '../../../models/category.model';

@Component({
  selector: 'app-product-list',
  standalone: false,
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  public products: Product[] = [];
  public categories: Category[] = [];
  public selectedCategoryId: number | null = null;
  public searchTerm: string = '';

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    public userService: UserService
  ) {}

  ngOnInit() {
    this.loadCategories();
    this.loadAll();
  }

  loadCategories() {
    this.categoryService.getAll().subscribe({
      next: (res) => (this.categories = res.data.values as Category[]),
      error: (err) => console.error(err)
    });
  }

  loadAll() {
    this.selectedCategoryId = null;
    this.productService.getAll().subscribe({
      next: (res) => (this.products = res.data.values as Product[]),
      error: (err) => console.error(err)
    });
  }

  selectCategory(id: number | null) {
    this.selectedCategoryId = id;
    if (id === null) {
      this.loadAll();
      return;
    }
    this.productService.getByCategory(id).subscribe({
      next: (res) => (this.products = res.data.values as Product[]),
      error: (err) => console.error(err)
    });
  }

  search() {
    const term = this.searchTerm.trim();
    if (!term) {
      this.loadAll();
      return;
    }
    this.selectedCategoryId = null;
    this.productService.searchProducts(term).subscribe({
      next: (res) => (this.products = res.data.values as Product[]),
      error: (err) => console.error(err)
    });
  }

  // Kada card obrise proizvod, ukloni ga iz liste
  onProductRemoved(product: Product) {
    this.products = this.products.filter((p) => p.id !== product.id);
  }
}
