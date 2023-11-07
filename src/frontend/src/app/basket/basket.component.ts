import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.css']
})
export class BasketComponent {
  constructor(private router: Router, private http: HttpClient, private cookieservice: CookieService) {}

  id = "";
  timestamp = "";
  name: string[] = [];
  price: string[] = [];
  basket = "";
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  response: any = null;
  
  // eslint-disable-next-line @angular-eslint/use-lifecycle-interface
  async ngOnInit(): Promise<void> {
    try {
      this.response = await this.getBasketOfClient();

      if (this.response != null) {
        await this.getProductsFromOrder(this.response);
      } else {
        console.log('Koszyk jest pusty');
        this.basket = 'Pusty';
      }
    } catch (error) {
      console.error('Błąd podczas pobierania danych', error);
    }
  }

  async getBasketOfClient() {
    try {
      const client = {
        email: this.cookieservice.get('SESSION_TOKEN')
      };

      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const response: any = await this.http.post('http://localhost:8082/getBasketOfClient', client).toPromise();
      return response;
    } catch (error) {
      console.error('Błąd podczas pobierania danych', error);
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  async getProductsFromOrder(response: any) {
    try {
      this.id = response.id;
        this.timestamp = response.basketFinish;
        const order = {
          order_id: response.id
        };

        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        const orderResponse: any = await this.http.post('http://localhost:8082/getProductsFromOrder', order).toPromise();

        for (const value of orderResponse) {
          this.name.push(value.name);
          this.price.push(value.price);
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
      const index = this.name.indexOf(name);
      if (index !== -1) {
        this.name.splice(index, 1);
        this.price.splice(index, 1);
        this.response = await this.getBasketOfClient();
        await this.getProductsFromOrder(this.response);
      }
    } catch (error) { 
      console.error('Błąd podczas usuwania produktu z koszyka', error);
    }
  }

  async order(): Promise<void> {
    try {
      const basket = {
        id: this.id
      };

      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const response: any = await this.http.post('http://localhost:8082/order', basket).toPromise();

      console.log('Zamówienie zostało złożone:', response);
      this.router.navigate(['history']);
    } catch (error) {
      console.error('Błąd podczas składania zamówienia', error);
    }
  }
}

