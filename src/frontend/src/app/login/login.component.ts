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

    this.http.post('http://localhost:8080/login', userData).subscribe(
      (response: any) => {
        console.log('Logowanie zakończona sukcesem', response);
      },
      (error) => {
        console.error('Błąd podczas logowania', error);
      }
    );
  }
  directToRegistry() {
    this.router.navigate(['registry']);
  }
}