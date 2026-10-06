import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Category } from '../models/category.model';

@Injectable({ providedIn: 'root' })
export class CategoryService {
  constructor(private http: HttpClient) {}

  public getAll(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/categories/all`);
  }

  public add(category: Category): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/categories/add`, category);
  }

  public update(category: Category): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/categories/update`, category);
  }

  public delete(id: number): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/categories/delete`, id);
  }
}
