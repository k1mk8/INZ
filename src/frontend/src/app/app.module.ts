import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContactComponent } from './contact/contact.component';
import { MainPageComponent } from './main-page/main-page.component';
import { MyAccountComponent } from './my-account/my-account.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { RegistryComponent } from './registry/registry.component';
import { MenuComponent } from './menu/menu.component';
import { Venus3DLComponent } from './venus3-dl/venus3-dl.component';
import {CookieService} from 'ngx-cookie-service';

@NgModule({
  declarations: [
    AppComponent,
    ContactComponent,
    MainPageComponent,
    MyAccountComponent,
    LoginComponent,
    RegistryComponent,
    MenuComponent,
    Venus3DLComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
  ],
  providers:
  [CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
