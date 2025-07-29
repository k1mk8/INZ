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
  email = '';
  password = '';
  message = '';

  constructor(
    private router: Router,
    private http: HttpClient,
    private cookieservice: CookieService
  ) {}

  async login(): Promise<void> {
    this.message = '';
    const payload = {
      email: this.email,
      password: this.password
    };
    try {
      const response = await this.http.post<boolean>(
        'http://localhost:8082/login',
        payload
      ).toPromise();

      if (response != null) {
        if(response){
          this.cookieservice.set('SESSION_TOKEN', this.email, 1/24);
          this.cookieservice.set('SESSION_ADMIN', 'YES', 1/24);
          this.router.navigate(['admin']);
        }
        else{
          this.message = 'Nieprawidłowy email lub hasło';
        }
      } 
    } catch (error) {
      console.error('Błąd podczas logowania', error);
      this.message = 'Błąd podczas logowania';
    }
  }
}