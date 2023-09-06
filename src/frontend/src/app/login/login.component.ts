import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private router: Router) {}
  email: string = '';
  password: string = '';

  login() {
    // Tutaj można dodać kod do wysłania danych logowania na serwer
    // i obsługi odpowiedzi z serwera, np. autoryzacji.
    // Możesz użyć Angular HTTP Client lub innych narzędzi do komunikacji z serwerem.
  }
  directToRegistry() {
    this.router.navigate(['registry']);
  }
}