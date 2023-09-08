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

  fetchDataFromServer() {
    const url = 'http://localhost:8080/getUser';

    this.http.get(url).subscribe((data: any) => {
      // Odbieranie danych z serwera
      const email = data.email;
      const hash = data.hash;

      // Możesz teraz wykorzystać email i hash w swojej aplikacji
      console.log('Email:', email);
      console.log('Hash:', hash);
      localStorage.setItem('isLoggedIn', 'true');
    },
    (error) => {
      console.error('Błąd podczas logowania', error);
        this.message = 'Nieprawidłowy email lub hasło. Proszę spróbować ponownie.';
    }
    );
  }

  login() {
    const userData = {
      password: this.email
    };

    this.http.post('http://localhost:8080/login', userData).subscribe(
      (response: any) => {
        console.log('Logowanie zakończona sukcesem', response);
      },
      (error) => {
        console.error('Błąd podczas logowania', error);
      }
    );
    this.fetchDataFromServer();

  }
  directToRegistry() {
    this.router.navigate(['registry']);
  }
}