import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { concatMap } from 'rxjs/operators';

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
  
  ngOnInit() {
    const client = {
      email: this.cookieservice.get('SESSION_TOKEN')
    };

    this.http.post('http://localhost:8082/getOrders', client).pipe(
      concatMap((response: any) => {
        // Use concatMap to ensure sequential execution
        return response;
      })
    ).subscribe(
      (element: any) => {
        this.id.push(element.id);
        this.state.push(element.state);
        this.timestamp.push(element.timestamp);

        this.name.push([]);
        this.price.push([]);
        const id = {
          order_id: element.id
        };
        // Use a separate function to handle the inner subscribe
        this.handleInnerSubscribe(id);
      },
      (error) => {
        console.error('Błąd podczas pobierania danych', error);
      }
    );
  }

  // Function to handle the inner subscribe
  private handleInnerSubscribe(id: any) {
    this.http.post('http://localhost:8082/getProductsFromOrder', id).subscribe(
      (response: any) => {
        for (const value of response) {
          this.name[this.idx].push(value.name);
          this.price[this.idx].push(value.price);
        }
        this.idx += 1; // Increment idx inside this callback
      },
      (error) => {
        console.error('Błąd podczas pobierania danych', error);
      }
    );
  }
}
