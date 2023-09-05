import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8082'; // Adres Twojego endpointa

  constructor(private http: HttpClient) {}

  // Metoda do logowania
  login(email: string, password: string): Promise<boolean> {
    const body = { email, password };

    return this.http
      .post<any>(`${this.apiUrl}/login`, body)
      .toPromise()
      .then((response) => {
        // Tutaj dodaj logikę weryfikacji odpowiedzi z serwera.
        // Możesz zwrócić true, jeśli logowanie się powiedzie,
        // lub false w przeciwnym przypadku.
        return response.success;
      })
      .catch((error) => {
        console.error('Błąd logowania:', error);
        return false; // Obsługa błędu logowania
      });
  }
}