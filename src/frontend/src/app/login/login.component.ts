import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgTemplateOutlet } from '@angular/common';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  login() {
    // Tutaj można dodać kod do wysłania danych logowania na serwer
    // i obsługi odpowiedzi z serwera, np. autoryzacji.
    // Możesz użyć Angular HTTP Client lub innych narzędzi do komunikacji z serwerem.
  }
}