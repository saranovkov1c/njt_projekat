import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from './services/user.service';
import { ClientService } from './services/client.service';
import { CartService } from './services/cart.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: false
})
export class AppComponent {
  title = 'suplementi-frontend';

  constructor(
    public userService: UserService,
    public clientService: ClientService,
    public cartService: CartService,
    private router: Router
  ) {}

  logout() {
    if (this.userService.getUserStatus() === 'admin') {
      this.userService.logOut();
    }
    if (this.clientService.getUserStatus() === 'user') {
      this.clientService.logOut();
    }
    this.router.navigate(['/products']);
  }
}
