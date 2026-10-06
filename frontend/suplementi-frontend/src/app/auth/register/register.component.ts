import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client.model';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  public registerForm!: FormGroup;

  constructor(
    private clientService: ClientService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.registerForm = formBuilder.group({      
      username: ['', Validators.required],
      password: ['', Validators.required],
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      phone: [''],
      birthday: ['', Validators.required]
    });
  }

  register() {
    if (!this.registerForm.valid) {
      alert('Molimo popunite sva obavezna polja!');
      return;
    }

    const client: Client = this.registerForm.value;

    this.clientService.addNewClient(client).subscribe({
      next: (res) => {
        if (res.message === 'This client already has an account!') {
          alert('Klijent sa tim podacima već postoji!');
          return;
        }
        alert('Nalog uspešno kreiran! Možete se prijaviti.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        alert('Greška pri registraciji.');
        console.error(err);
      }
    });
  }
}
