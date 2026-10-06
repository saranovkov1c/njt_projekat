import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CredentialsInterceptor } from './credentials.interceptor';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';

import { ProductListComponent } from './components/products/product-list/product-list.component';
import { ProductCardComponent } from './components/products/product-card/product-card.component';
import { ProductAddComponent } from './components/products/product-add/product-add.component';
import { ProductUpdateModalComponent } from './components/products/product-update-modal/product-update-modal.component';

import { ClientListComponent } from './components/clients/client-list/client-list.component';
import { ClientAddComponent } from './components/clients/client-add/client-add.component';
import { ClientUpdateModalComponent } from './components/clients/client-update-modal/client-update-modal.component';

import { OrderListComponent } from './components/orders/order-list/order-list.component';
import { MyOrdersComponent } from './components/orders/my-orders/my-orders.component';
import { CartComponent } from './components/orders/cart/cart.component';
import { MyAddressesComponent } from './components/addresses/my-addresses.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProductListComponent,
    ProductCardComponent,
    ProductAddComponent,
    ProductUpdateModalComponent,
    ClientListComponent,
    ClientAddComponent,
    ClientUpdateModalComponent,
    OrderListComponent,
    MyOrdersComponent,
    CartComponent,
    MyAddressesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: CredentialsInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
