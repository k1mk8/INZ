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
import { BasketComponent } from './basket/basket.component';
import { HistoryComponent } from './history/history.component';
import { OliwiaIII3dlComponent } from './oliwia-iii3dl/oliwia-iii3dl.component';
import { VenusFotelComponent } from './venus-fotel/venus-fotel.component';
import { MatrixComponent } from './matrix/matrix.component';
import { SaraComponent } from './sara/sara.component';

@NgModule({
  declarations: [
    AppComponent,
    ContactComponent,
    MainPageComponent,
    MyAccountComponent,
    LoginComponent,
    RegistryComponent,
    MenuComponent,
    Venus3DLComponent,
    BasketComponent,
    HistoryComponent,
    OliwiaIII3dlComponent,
    VenusFotelComponent,
    MatrixComponent,
    SaraComponent
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
