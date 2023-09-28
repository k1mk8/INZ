import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-divanjas',
  templateUrl: './divanjas.component.html',
  styleUrls: ['./divanjas.component.css']
})
export class DivanjasComponent {
  constructor(private router: Router, private http: HttpClient, private cookieservice: CookieService) {}

  name: string = "Tapczan Jaś";
  timing: string = "";
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

    this.http.post('http://localhost:8082/checkSchedule', productData).subscribe(
      (response: any) => {
        if (response.date != null)
        {
          console.log('Czas oczekiwania wynosi: ', response.date);
          this.timing = response.date;
        }
        else
        {
          console.log('Czas oczekiwania wynosi ponad miesiąc');
          this.timing = "Czas oczekiwania wynosi ponad miesiąc";
        }
      },
      (error) => {
        console.error('Błąd podczas pobierania danych', error);
      }
    );
  }

  addToBasket() {
    if (!this.cookieservice.check('SESSION_TOKEN'))
    {
      this.router.navigate(['login']);
      return;
    }
    const productData = {
      email: this.cookieservice.get('SESSION_TOKEN'),
      name: this.name,
      amount: 1
    };
    this.http.post('http://localhost:8082/addToBasket', productData).subscribe();
    this.router.navigate(['basket']);
  } 

  openImageInNewWindow() {
    window.open('../../assets/divanJas.jpg', '_blank');
  }
}
