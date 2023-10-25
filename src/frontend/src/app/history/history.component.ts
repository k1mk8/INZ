import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent {
  constructor(private router: Router, private http: HttpClient, private cookieservice: CookieService) {}

  id: string[] = [];
  state: string[] = [];
  timestamp: string[] = [];

  name: string[][] = [];
  price: string[][] = [];

  idx: number = 0;
  
  async ngOnInit() {
    const client = {
      email: this.cookieservice.get('SESSION_TOKEN')
    };
  
    try {
      const response = await this.http.post('http://localhost:8082/getOrders', client).toPromise();
  
      for (const element of response as any[]) {
        this.id.push(element.id);
        this.state.push(element.state);
        this.timestamp.push(element.timestamp);
  
        this.name.push([]);
        this.price.push([]);
  
        const id = {
          order_id: element.id
        };
  
        await this.handleInnerSubscribe(id);
      }
    } catch (error) {
      console.error('Błąd podczas pobierania danych', error);
    }
  }
  
  async handleInnerSubscribe(id: any) {
    try {
      const innerResponse = await this.http.post('http://localhost:8082/getProductsFromOrder', id).toPromise();
  
      for (const value of innerResponse as any[]) {
        this.name[this.idx].push(value.name);
        this.price[this.idx].push(value.price);
      }
  
      this.idx += 1; 
    } catch (error) {
      console.error('Błąd podczas pobierania danych', error);
    }
  }
}