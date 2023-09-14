import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-venus3-dl',
  templateUrl: './venus3-dl.component.html',
  styleUrls: ['./venus3-dl.component.css']
})
export class Venus3DLComponent {
  constructor(private http: HttpClient) {}

  name: string = "Knz Venus";
  avialability: string = 'Sprawdzanie dostepnosci';

  ngOnInit() {
    const productData = {
      name: this.name
    };

    this.http.post('http://localhost:8082/checkAvailability', productData).subscribe(
      (response: any) => {
        if (response == true)
        {
          console.log('Produkt dostępny');
          this.avialability = "Produkt dostępny";
        }
        else
        {
          console.log('Produkt niedostępny');
          this.avialability = "Produkt niedostępny";
        }
      },
      (error) => {
        console.error('Błąd podczas pobierania danych', error);
      }
    );
  }

  openImageInNewWindow() {
    window.open('../../assets/venus3dl.jpg', '_blank');
  }
}
