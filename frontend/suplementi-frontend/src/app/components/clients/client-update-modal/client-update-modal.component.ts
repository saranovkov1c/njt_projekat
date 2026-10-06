import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Client } from '../../../models/client.model';

@Component({
  selector: 'app-client-update-modal',
  standalone: false,
  templateUrl: './client-update-modal.component.html',
  styleUrls: ['./client-update-modal.component.css']
})
export class ClientUpdateModalComponent implements OnInit {
  @Input() client!: Client;
  @Output() saveClient = new EventEmitter<Client>();
  @Output() closeModal = new EventEmitter<void>();

  public updateForm!: FormGroup;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.updateForm = this.formBuilder.group({
      username: [this.client.username, Validators.required],
      password: [this.client.password, Validators.required],
      firstname: [this.client.firstname, Validators.required],
      lastname: [this.client.lastname, Validators.required],
      phone: [this.client.phone],
      birthday: [this.client.birthday, Validators.required]
    });
  }

  save() {
    if (!this.updateForm.valid) { alert('Popunite sva polja!'); return; }
    const updated: Client = { id: this.client.id, ...this.updateForm.value };
    this.saveClient.emit(updated);
  }

  close() { this.closeModal.emit(); }
}
