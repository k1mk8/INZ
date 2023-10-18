import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registry',
  templateUrl: './registry.component.html',
  styleUrls: ['./registry.component.css']
})
export class RegistryComponent {
  constructor(private http: HttpClient, private router: Router) {}
  registerEmail: string = '';
  registerPassword: string = '';
  registerFirstName: string = '';
  registerLastName: string = '';
  registerPhoneNumber: string = '';
  message: string = '';

  async register(): Promise<void> {
    const userData = {
      email: this.registerEmail,
      password: this.registerPassword,
      name: this.registerFirstName,
      surname: this.registerLastName,
      number: this.registerPhoneNumber
    };

    try {
      const response: any = await this.http.post('http://localhost:8082/register', userData).toPromise();

      if (response === true) {
        console.log('Rejestracja zakończona sukcesem', response);
        this.message = 'Logowanie prawidłowe';
        this.router.navigate(['login']);
      } else {
        this.message = 'Użytkownik z takim emailem istnieje w systemie';
        console.log('Użytkownik z takim emailem istnieje w systemie');
      }
    } catch (error) {
      console.error('Błąd podczas rejestracji', error);
      this.message = 'Błąd podczas rejestracji. Proszę spróbować ponownie.';
    }
  }
}