import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private router: Router, private http: HttpClient) {}
  
  email: string = '';
  password: string = '';
  message: string = '';

  login() {
    // Tutaj można dodać kod do wysłania danych logowania na serwer
    // i obsługi odpowiedzi z serwera, np. autoryzacji.
    // Możesz użyć Angular HTTP Client lub innych narzędzi do komunikacji z serwerem.
  }
  fetchDataFromServer() {
    const url = 'http://localhost:8080/getUser';

    this.http.get(url).subscribe((data: any) => {
      // Odbieranie danych z serwera
      const email = data.email;
      const hash = data.hash;

      // Możesz teraz wykorzystać email i hash w swojej aplikacji
      console.log('Email:', email);
      console.log('Hash:', hash);
    },
    (error) => {
      console.error('Błąd podczas logowania', error);
        this.message = 'Nieprawidłowy email lub hasło. Proszę spróbować ponownie.';
    }
    );
  }
  directToRegistry() {
    this.router.navigate(['registry']);
  }
}