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
    const userData = {
      email: this.email,
      password: this.password
    };

    this.http.post('http://localhost:8082/login', userData).subscribe(
      (response: any) => {
        if (response == true)
        {
          console.log('Logowanie zakończona sukcesem', response);
          localStorage.setItem('loggedIn', this.email);
          this.message = 'Logowanie prawidłowe';
          this.directToMyAccount();
        }
        else
        {
          this.message = 'Nieprawidłowy email lub hasło';
          console.log('Nieprawidłowy email lub haslo');
        }
      },
      (error) => {
        console.error('Błąd podczas logowania', error);
        this.message = 'Błąd podczas logowania';
      }
    );
  }
  directToRegistry() {
    this.router.navigate(['registry']);
  }
  directToMyAccount() {
    this.router.navigate(['myaccount']);
  }
}