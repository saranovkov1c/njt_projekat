import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Client } from '../models/client.model';

@Injectable({ providedIn: 'root' })
export class ClientService {
  private currentClient!: Client;

  constructor(private http: HttpClient) {}

  public getAll(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/clients/all`);
  }

  public addNewClient(client: Client): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/clients/add`, client);
  }

  public deleteClient(id: number): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/clients/delete`, id);
  }

  public updateClient(client: Client): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/clients/update`, client);
  }

  public searchClients(search: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/clients/search/${search}`);
  }

  public getClient(id: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/clients/getClient/${id}`);
  }

  public login(username: string, password: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/clients/login/${username}/${password}`);
  }

  // ---- Sesija (klijent) ----
  public getUserStatus() {
    return sessionStorage.getItem('currentUser');
  }

  public setClient(client: Client) {
    this.currentClient = client;
    sessionStorage.setItem('client', JSON.stringify(client));
  }

  public getSpecificClient(): Client {
    if (!this.currentClient) {
      const s = sessionStorage.getItem('client');
      if (s) this.currentClient = JSON.parse(s);
    }
    return this.currentClient;
  }

  public setSessionData(client: Client) {
    sessionStorage.setItem('user', client.username);
    sessionStorage.setItem('currentUser', 'user');
    this.setClient(client);
  }

  public logOut() {
    sessionStorage.clear();
    this.currentClient = new Client();
  }
}
