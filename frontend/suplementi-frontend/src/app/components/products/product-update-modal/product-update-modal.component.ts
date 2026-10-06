import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Product } from '../../../models/product.model';
import { CategoryService } from '../../../services/category.service';
import { Category } from '../../../models/category.model';

@Component({
  selector: 'app-product-update-modal',
  standalone: false,
  templateUrl: './product-update-modal.component.html',
  styleUrls: ['./product-update-modal.component.css']
})
export class ProductUpdateModalComponent implements OnInit {
  @Input() product!: Product;
  @Output() saveProduct = new EventEmitter<Product>();
  @Output() closeModal = new EventEmitter<void>();

  public updateForm!: FormGroup;
  public categories: Category[] = [];
  public forms: string[] = ['POWDER', 'CAPSULE', 'TABLET', 'LIQUID', 'BAR', 'GUMMY'];

  constructor(private formBuilder: FormBuilder, private categoryService: CategoryService) {}

  ngOnInit() {
    this.updateForm = this.formBuilder.group({
      name: [this.product.name, Validators.required],
      description: [this.product.description],
      price: [this.product.price, [Validators.required, Validators.min(0)]],
      stock: [this.product.stock, [Validators.required, Validators.min(0)]],
      form: [this.product.form, Validators.required],
      imageUrl: [this.product.imageUrl],
      categoryId: [this.product.category?.id, Validators.required]
    });

    this.categoryService.getAll().subscribe({
      next: (res) => (this.categories = res.data.values as Category[])
    });
  }

  save() {
    if (!this.updateForm.valid) {
      alert('Molimo popunite sva obavezna polja!');
      return;
    }
    const v = this.updateForm.value;
    const updated: any = {
      id: this.product.id,
      name: v.name,
      description: v.description,
      price: v.price,
      stock: v.stock,
      form: v.form,
      imageUrl: v.imageUrl,
      category: { id: v.categoryId }
    };
    this.saveProduct.emit(updated);
  }

  close() {
    this.closeModal.emit();
  }
}
