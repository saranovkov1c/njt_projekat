import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AddressService } from '../../services/address.service';
import { ClientService } from '../../services/client.service';
import { Address } from '../../models/address.model';

@Component({
  selector: 'app-my-addresses',
  standalone: false,
  templateUrl: './my-addresses.component.html',
  styleUrls: ['./my-addresses.component.css']
})
export class MyAddressesComponent implements OnInit {
  public addresses: Address[] = [];
  public addressForm!: FormGroup;
  public showForm = false;

  constructor(
    private addressService: AddressService,
    private clientService: ClientService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.addressForm = this.formBuilder.group({
      street: ['', Validators.required],
      number: ['', Validators.required],
      city: ['', Validators.required],
      postal_code: ['', Validators.required]
    });
    this.loadAddresses();
  }

  loadAddresses() {
    const client = this.clientService.getSpecificClient();
    if (!client) return;
    this.addressService.getByClient(client.id).subscribe({
      next: (res) => (this.addresses = res.data.values as Address[]),
      error: (err) => console.error(err)
    });
  }

  save() {
    if (!this.addressForm.valid) { alert('Molimo popunite sva polja!'); return; }
    const client = this.clientService.getSpecificClient();
    const v = this.addressForm.value;
    const address: any = {
      street: v.street,
      number: v.number,
      city: v.city,
      postal_code: v.postal_code,
      client: { id: client.id }
    };
    this.addressService.add(address).subscribe({
      next: () => {
        alert('Adresa sačuvana!');
        this.showForm = false;
        this.loadAddresses();
      },
      error: (err) => { console.error(err); alert('Čuvanje adrese nije uspelo!'); }
    });
  }

  delete(address: Address) {
    if (!confirm('Obrisati ovu adresu?')) return;
    this.addressService.delete(address.id).subscribe({
      next: () => {
        alert('Adresa obrisana!');
        this.addresses = this.addresses.filter((a) => a.id !== address.id);
      },
      error: (err) => { console.error(err); alert('Brisanje nije uspelo!'); }
    });
  }
}