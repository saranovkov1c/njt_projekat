import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Order } from '../models/order.model';
import { Client } from '../models/client.model';

@Injectable({ providedIn: 'root' })
export class OrderService {
  constructor(private http: HttpClient) {}

  public getAll(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/orders/all`);
  }

  public nextId(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/orders/nextId`);
  }

  public addNewOrder(order: Order): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/orders/add`, order);
  }

  public delete(id: number): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/orders/delete`, id);
  }

  public getClientsOrders(client: Client): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/orders/getClientsOrders`, client);
  }

  public createOrder(order: any): Observable<any> {
  return this.http.post<any>(`${environment.apiUrl}/orders/create`, order);
}
}
