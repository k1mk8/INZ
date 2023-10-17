import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-divankuba',
  templateUrl: './divankuba.component.html',
  styleUrls: ['./divankuba.component.css']
})
export class DivankubaComponent {
  constructor(private router: Router, private http: HttpClient, private cookieservice: CookieService) {}

  name: string = "Tapczan Kuba";
  timing: string = "";
  availability: string = 'Sprawdzanie dostepnosci';

  async ngOnInit() {
    const productData = {
      name: this.name
    };

    try {
      const availabilityResponse = await this.http.post<boolean>('http://localhost:8082/checkAvailability', productData).toPromise();
      
      if (availabilityResponse) {
        console.log('Produkt dostępny');
        this.availability = "Produkt dostępny";
      } else {
        console.log('Produkt niedostępny');
        this.availability = "Produkt niedostępny";
      }

      const scheduleResponse = await firstValueFrom(this.http.post<{ date: string }>('http://localhost:8082/checkSchedule', productData));

      if (scheduleResponse && scheduleResponse.date != null) {
        console.log('Czas oczekiwania wynosi: ', scheduleResponse.date);
        this.timing = scheduleResponse.date;
      } else {
        console.log('Czas oczekiwania wynosi ponad miesiąc');
        this.timing = "Czas oczekiwania wynosi ponad miesiąc";
      }
    } catch (error) {
      console.error('Błąd podczas pobierania danych', error);
    }
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
    window.open('../../assets/divanKuba.jpg', '_blank');
  }
}
