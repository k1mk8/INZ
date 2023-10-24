import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private router: Router, private http: HttpClient, private cookieservice: CookieService) {}
  
  email: string = '';
  password: string = '';
  message: string = '';

  async login(): Promise<void> { 
    const userData = {
      email: this.email,
      password: this.password
    };

    try {
      const response: any = await this.http.post('http://localhost:8082/login', userData).toPromise();

      if (response === true) {
        console.log('Logowanie zakończona sukcesem', response);
        this.cookieservice.set('SESSION_TOKEN', this.email, 1/24);
        this.message = 'Logowanie prawidłowe';
        this.directToMyAccount();
      } else {
        this.message = 'Nieprawidłowy email lub hasło';
        console.log('Nieprawidłowy email lub hasło');
      }
    } catch (error) {
      console.error('Błąd podczas logowania', error);
      this.message = 'Błąd podczas logowania'; 
    }
  }
  
  directToRegistry() {
    this.router.navigate(['registry']); 
  }
  directToMyAccount() {
    this.router.navigate(['myaccount']);
  }
}