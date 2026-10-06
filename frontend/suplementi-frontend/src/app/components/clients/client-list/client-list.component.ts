import { Component, OnInit } from '@angular/core';
import { ClientService } from '../../../services/client.service';
import { Client } from '../../../models/client.model';

@Component({
  selector: 'app-client-list',
  standalone: false,
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  public clients: Client[] = [];
  public editingClient: Client | null = null;

  constructor(private clientService: ClientService) {}

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.clientService.getAll().subscribe({
      next: (res) => (this.clients = res.data.values as Client[]),
      error: (err) => console.error(err)
    });
  }

  edit(client: Client) {
    this.editingClient = client;
  }

  onSave(updated: Client) {
    this.clientService.updateClient(updated).subscribe({
      next: () => {
        alert('Klijent ažuriran!');
        this.editingClient = null;
        this.loadAll();
      },
      error: (err) => { console.error(err); alert('Ažuriranje nije uspelo!'); }
    });
  }

  delete(client: Client) {
    if (!confirm('Obrisati ovog klijenta?')) return;
    this.clientService.deleteClient(client.id).subscribe({
      next: () => {
        alert('Klijent obrisan!');
        this.clients = this.clients.filter((c) => c.id !== client.id);
      },
      error: (err) => { console.error(err); alert('Brisanje nije uspelo (možda ima porudžbine).'); }
    });
  }
}
