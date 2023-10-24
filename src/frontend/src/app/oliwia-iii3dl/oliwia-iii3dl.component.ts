import { Component, HostListener } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-oliwia-iii3dl',
  templateUrl: './oliwia-iii3dl.component.html',
  styleUrls: ['./oliwia-iii3dl.component.css']
})
export class OliwiaIII3dlComponent {
  constructor(public router: Router, private http: HttpClient, public cookieservice: CookieService) {}

  name: string = "Oliwia III 3DL";
  timing: string = "";
  availability: string = 'Sprawdzanie dostepnosci';

  tableVisible = false;

  @HostListener('window:scroll', [])
  onWindowScroll() {
    const table = document.getElementById('myTable');
    if (table) {
      const rect = table.getBoundingClientRect();
      const windowHeight = window.innerHeight;
      if (rect.top < windowHeight) {
        this.tableVisible = true;
      }
    }
  }

  async ngOnInit() {

    try {
      this.checkAvailability();
      this.checkSchedule();
    } catch (error) {
      console.error('Błąd podczas pobierania danych', error);
    }
  }

  async checkAvailability() {
    const productData = {
      name: this.name
    };
    const availabilityResponse = await this.http.post<boolean>('http://localhost:8082/checkAvailability', productData).toPromise();
      
      if (availabilityResponse) {
        console.log('Produkt dostępny');
        this.availability = "Produkt dostępny";
      } else {
        console.log('Produkt niedostępny');
        this.availability = "Produkt niedostępny";
      }
  }

  async checkSchedule() {
    const productData = {
      name: this.name
    };

    const scheduleResponse = await firstValueFrom(this.http.post<{ date: string }>('http://localhost:8082/checkSchedule', productData));

      if (scheduleResponse && scheduleResponse.date != null) {
        console.log('Czas oczekiwania wynosi: ', scheduleResponse.date);
        this.timing = scheduleResponse.date;
      } else {
        console.log('Czas oczekiwania wynosi ponad miesiąc');
        this.timing = "Czas oczekiwania wynosi ponad miesiąc";
      }
  }

  async addToBasket(): Promise<void> {
    if (!this.cookieservice.check('SESSION_TOKEN')) {
      this.router.navigate(['login']);
      return;
    }

    const productData = {
      email: this.cookieservice.get('SESSION_TOKEN'),
      name: this.name,
      amount: 1
    };

    try {
      await this.http.post('http://localhost:8082/addToBasket', productData).toPromise();
      this.router.navigate(['basket']);
    } catch (error) {
      console.error('Błąd podczas dodawania produktu do koszyka', error);
    }
  }

  openImageInNewWindow() {
    window.open('../../assets/oliwiaIII3dl.jpg', '_blank');
  }
}
