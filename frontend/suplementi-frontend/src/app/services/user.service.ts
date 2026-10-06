import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Response } from '../connection/response';
import { User } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) {}


  public login(username: string, password: string): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/users/login/${username}/${password}`);
  }

  public getAll(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/users/all`);
  }

  // ---- Sesija (admin) ----
  public setSessionData(user: any) {
    sessionStorage.setItem('user', JSON.stringify(user));
    sessionStorage.setItem('userId', String(user.id));
    sessionStorage.setItem('currentUser', 'admin');
  }

  public getLoggedInUser(): User | null {
    const s = sessionStorage.getItem('user');
    return s ? JSON.parse(s) as User : null;
  }

  public getUserStatus() {
    return sessionStorage.getItem('currentUser');
  }

  public getUserId(): number {
    return Number(sessionStorage.getItem('userId') || 0);
  }

  public logOut() {
    sessionStorage.clear();
  }
}
