import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-registry',
  templateUrl: './registry.component.html',
  styleUrls: ['./registry.component.css']
})
export class RegistryComponent {
  registerEmail: string = '';
  registerPassword: string = '';
  registerFirstName: string = '';
  registerLastName: string = '';
  registerPhoneNumber: string = '';
  message: string = '';

  constructor(private http: HttpClient) {}

  register() {
    // Dane do wysłania na serwer
    const userData = {
      email: this.registerEmail,
      password: this.registerPassword,
      firstName: this.registerFirstName,
      lastName: this.registerLastName,
      phoneNumber: this.registerPhoneNumber
    };

    // Wysłanie danych na serwer
    this.http.post('http://localhost:8082/register', userData).subscribe(
      (response: any) => {
        console.log('Rejestracja zakończona sukcesem', response);
        this.message = 'Błąd podczas rejestracji. Proszę spróbować ponownie.';
      },
      (error) => {
        console.error('Błąd podczas rejestracji', error);
        this.message = 'Błąd podczas rejestracji. Proszę spróbować ponownie.';
      }
    );
  }
}