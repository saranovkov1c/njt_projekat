import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { OrderItem } from '../models/order-item.model';

@Injectable({ providedIn: 'root' })
export class OrderItemService {
  constructor(private http: HttpClient) {}

  public nextId(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/order-items/nextId`);
  }

  public add(item: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/order-items/add`, item);
  }

  public getByOrder(orderId: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/order-items/order/${orderId}`);
  }
}
