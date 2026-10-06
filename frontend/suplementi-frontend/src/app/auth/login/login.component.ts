import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client.model';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  public loginForm!: FormGroup;

  constructor(
    private userService: UserService,
    private clientService: ClientService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.loginForm = formBuilder.group({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  login() {
    if (!this.loginForm.valid) {
      alert('Morate popuniti oba polja!');
      return;
    }

    const username = this.loginForm.get('username')!.value;
    const password = this.loginForm.get('password')!.value;

    
    this.userService.login(username, password).subscribe({
      next: (user) => {

        if (user && user.role === 'ADMIN') {
          this.userService.setSessionData(user);
          this.router.navigate(['/products']);
        } else {
          
          this.tryClientLogin(username, password);
        }
      },
      error: () => {
        
        this.tryClientLogin(username, password);
      }
    });
  }

  private tryClientLogin(username: string, password: string) {
    this.clientService.login(username, password).subscribe({
      next: (client) => {
        
        if (client && client.id) {
          this.clientService.setSessionData(client);
          this.router.navigate(['/products']);
        } else {
          alert('Pogrešno korisničko ime ili lozinka!');
        }
      },
      error: () => {
        alert('Pogrešno korisničko ime ili lozinka!');
      }
    });
  }
}
