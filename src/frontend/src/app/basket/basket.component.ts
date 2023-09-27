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
  
  ngOnInit() {
    const client = {
      email: this.cookieservice.get('SESSION_TOKEN')
    };

    this.http.post('http://localhost:8082/getBasketOfClient', client).subscribe(
      (response: any) => {
        if (response != null)
        {
          this.id = response.id;
          const order = {
            order_id: response.id
          };
          this.http.post('http://localhost:8082/getProductsFromOrder', order).subscribe(
            (response: any) => {
              for (const value of response) {
                this.name.push(value.name);
                this.price.push(value.price);
              }
            },
            (error) => {
              console.error('Błąd podczas pobierania danych', error);
            }
          );
        }
        else
        {
          console.log('Koszyk jest pusty');
        }
      },
      (error) => {
        console.error('Błąd podczas pobierania danych', error);
      }
    );
  }

  removeFromBasket(name : string) {
    const product = {
      order_id : this.id ,
      product_name : name
    }
    this.http.post('http://localhost:8082/removeFromBasket', product).subscribe(
      (response: any) => {
      }
    );
    window.location.reload();
  }
}

