import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {
  constructor(private http: HttpClient, private router: Router) {}
  registerDimensions: string = '';
  registerPrice: string = '';
  registerType: string = '';
  registerName: string = '';
  registerDescription: string = '';
  message: string = '';

  async registerProduct(): Promise<void> {
    const userData = {
      dimension: this.registerDimensions,
      price: this.registerPrice,
      type: this.registerType,
      name: this.registerName,
      description: this.registerDescription
    };

    try {
      const response: any = await this.http.post('http://localhost:8082/addProduct', userData).toPromise();

      if (response === true) {
        console.log('Rejestracja produktu zakończona sukcesem', response);
        this.message = 'rejestracja prawidłoa prawidłowe';
        this.router.navigate(['admin']);
      } else {
        this.message = 'Produkt o takiej nazwie istnieje w systemie';
        console.log('Produkt o takiej nazwie istnieje w systemie');
      }
    } catch (error) {
      console.error('Błąd podczas rejestracji', error);
      this.message = 'Błąd podczas rejestracji produktu. Proszę spróbować ponownie.';
    }
  }
  onImageSelected(event: any) {
    const selectedFile = event.target.files[0];
    if (selectedFile) {
      if (selectedFile.type === 'image/jpeg') {
        console.log('Zdjęcie zostało wybrane:', selectedFile);
      } else {
        console.log('Wybrany plik nie jest w formacie JPG.');
      }
    }
  }
}
