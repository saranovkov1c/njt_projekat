import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { ProductListComponent } from './components/products/product-list/product-list.component';
import { ProductAddComponent } from './components/products/product-add/product-add.component';
import { ClientListComponent } from './components/clients/client-list/client-list.component';
import { ClientAddComponent } from './components/clients/client-add/client-add.component';
import { OrderListComponent } from './components/orders/order-list/order-list.component';
import { MyOrdersComponent } from './components/orders/my-orders/my-orders.component';
import { CartComponent } from './components/orders/cart/cart.component';
import { MyAddressesComponent } from './components/addresses/my-addresses.component'; 

const routes: Routes = [
  // Autentifikacija - jedna login forma
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // Proizvodi (javno vidljivi, admin ima dodatne akcije)
  { path: 'products', component: ProductListComponent },
  { path: 'products/add', component: ProductAddComponent },

  // Klijenti (admin)
  { path: 'clients', component: ClientListComponent },
  { path: 'clients/add', component: ClientAddComponent },

  // Porudzbine
  { path: 'orders', component: OrderListComponent },   // admin - sve
  { path: 'my-orders', component: MyOrdersComponent },  // klijent - svoje
  { path: 'cart', component: CartComponent },           // klijent - korpa

  { path: 'my-addresses', component: MyAddressesComponent }, // klijent - adresa

  { path: '', redirectTo: '/products', pathMatch: 'full' },
  { path: '**', redirectTo: '/products' },

  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
