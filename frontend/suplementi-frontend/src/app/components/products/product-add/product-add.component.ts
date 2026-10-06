import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProductService } from '../../../services/product.service';
import { CategoryService } from '../../../services/category.service';
import { Category } from '../../../models/category.model';

@Component({
  selector: 'app-product-add',
  standalone: false,
  templateUrl: './product-add.component.html',
  styleUrls: ['./product-add.component.css']
})
export class ProductAddComponent implements OnInit {
  public productForm!: FormGroup;
  public categories: Category[] = [];
  public forms: string[] = ['POWDER', 'CAPSULE', 'TABLET', 'LIQUID', 'BAR', 'GUMMY'];

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit() {
    this.productForm = this.formBuilder.group({      
      name: ['', Validators.required],
      description: [''],
      price: ['', [Validators.required, Validators.min(0)]],
      stock: ['', [Validators.required, Validators.min(0)]],
      form: ['POWDER', Validators.required],
      imageUrl: [''],
      categoryId: ['', Validators.required]
    });

    this.categoryService.getAll().subscribe({
      next: (res) => (this.categories = res.data.values as Category[])
    });
  }

  save() {
    if (!this.productForm.valid) {
      alert('Molimo popunite sva obavezna polja!');
      return;
    }

    const v = this.productForm.value;
    const product: any = {
      id: v.id,
      name: v.name,
      description: v.description,
      price: v.price,
      stock: v.stock,
      form: v.form,
      imageUrl: v.imageUrl,
      category: { id: v.categoryId }
    };

    this.productService.addProduct(product).subscribe({
      next: () => {
        alert('Proizvod uspešno dodat!');
        this.router.navigate(['/products']);
      },
      error: (err) => {
        console.error(err);
        alert('Dodavanje nije uspelo!');
      }
    });
  }
}
