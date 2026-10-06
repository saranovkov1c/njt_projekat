import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class ProductService {
  constructor(private http: HttpClient) {}

  public getAll(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/products/all`);
  }

  public getByCategory(categoryId: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/products/category/${categoryId}`);
  }

  public searchProducts(search: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/products/search/${search}`);
  }

  public addProduct(product: Product): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/products/add`, product);
  }

  public updateProduct(product: Product): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/products/update`, product);
  }

  public delete(id: number): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/products/delete`, id);
  }
}
