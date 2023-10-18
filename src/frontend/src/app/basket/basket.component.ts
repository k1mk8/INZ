import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { concatMap } from 'rxjs/operators';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.css']
})
export class BasketComponent {
  constructor(private router: Router, private http: HttpClient, private cookieservice: CookieService) {}

  id: string = "";

  name: string[] = [];
  price: string[] = [];
  basket: string = "";
  
  async ngOnInit(): Promise<void> {
    try {
      const client = {
        email: this.cookieservice.get('SESSION_TOKEN')
      };

      const response: any = await this.http.post('http://localhost:8082/getBasketOfClient', client).toPromise();

      if (response != null) {
        this.id = response.id;
        const order = {
          order_id: response.id
        };

        const orderResponse: any = await this.http.post('http://localhost:8082/getProductsFromOrder', order).toPromise();

        for (const value of orderResponse) {
          this.name.push(value.name);
          this.price.push(value.price);
        }
      } else {
        console.log('Koszyk jest pusty');
        this.basket = 'Pusty';
      }
    } catch (error) {
      console.error('Błąd podczas pobierania danych', error);
    }
  }

  async removeFromBasket(name: string): Promise<void> {
    try {
      const product = {
        order_id: this.id,
        product_name: name
      };

      await this.http.post('http://localhost:8082/removeFromBasket', product).toPromise();
      window.location.reload();
    } catch (error) {
      console.error('Błąd podczas usuwania produktu z koszyka', error);
    }
  }

  async order(): Promise<void> {
    try {
      const basket = {
        id: this.id
      };

      const response: any = await this.http.post('http://localhost:8082/order', basket).toPromise();

      console.log('Zamówienie zostało złożone:', response);
      this.router.navigate(['history']);
    } catch (error) {
      console.error('Błąd podczas składania zamówienia', error);
    }
  }
}

