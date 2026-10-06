import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ClientService } from '../../../services/client.service';
import { Client } from '../../../models/client.model';

@Component({
  selector: 'app-client-add',
  standalone: false,
  templateUrl: './client-add.component.html',
  styleUrls: ['./client-add.component.css']
})
export class ClientAddComponent {
  public clientForm!: FormGroup;

  constructor(
    private clientService: ClientService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.clientForm = formBuilder.group({      
      username: ['', Validators.required],
      password: ['', Validators.required],
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      phone: [''],
      birthday: ['', Validators.required]
    });
  }

  save() {
    if (!this.clientForm.valid) {
      alert('Molimo popunite sva obavezna polja!');
      return;
    }
    const client: Client = this.clientForm.value;
    this.clientService.addNewClient(client).subscribe({
      next: (res) => {
        if (res.message === 'This client already has an account!') {
          alert('Klijent već postoji!');
          return;
        }
        alert('Klijent uspešno dodat!');
        this.router.navigate(['/clients']);
      },
      error: (err) => { console.error(err); alert('Dodavanje nije uspelo!'); }
    });
  }
}
