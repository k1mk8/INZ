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
  
  email = '';
  password = '';
  message = '';
  isAdmin = false;

  async login(): Promise<void> { 
    const userData = {
      email: this.email,
      password: this.password
    };

    try {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const response: any = await this.http.post('http://localhost:8082/login', userData).toPromise();

      if (response === 1 || response === 2) {
        console.log('Logowanie zakończona sukcesem', response);
        this.cookieservice.set('SESSION_TOKEN', this.email, 1/24);
        this.message = 'Logowanie prawidłowe';
        if(response === 2){
          this.cookieservice.set('SESSION_ADMIN', 'YES', 1/24);
          this.router.navigate(['admin']);
        }
        else{
          this.cookieservice.set('SESSION_ADMIN', 'NO', 1/24);
          this.router.navigate(['myaccount']);
        }
      } else {
        this.message = 'Nieprawidłowy email lub hasło';
        console.log('Nieprawidłowy email lub hasło');
      }
    } catch (error) {
      console.error('Błąd podczas logowania', error);
      this.message = 'Błąd podczas logowania'; 
    }
  }
  directToRegistry(){
    this.router.navigate(['registry']);
  }
}