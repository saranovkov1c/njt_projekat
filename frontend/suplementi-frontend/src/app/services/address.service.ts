import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Address } from '../models/address.model';

@Injectable({ providedIn: 'root' })
export class AddressService {
  constructor(private http: HttpClient) {}

  public getByClient(clientId: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/addresses/client/${clientId}`);
  }
  public add(address: Address): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/addresses/add`, address);
  }
  public delete(id: number): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/addresses/delete`, id);
  }
}