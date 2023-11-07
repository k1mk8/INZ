import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent implements OnInit {
  constructor(public cookieService: CookieService, private http: HttpClient, public router: Router) {}

  name = '';
  surname = '';
  number = '';
  email = '';

  async ngOnInit(): Promise<void> {
    this.email = this.cookieService.get('SESSION_TOKEN');

    try {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const response: any = await this.http.post('http://localhost:8082/clientByEmail', this.email).toPromise();

      if (response != null) {
        console.log('Użytkownik zalogowany', response);
        this.name = response.name;
        this.surname = response.surname;
        this.number = response.number;
      } else {
        console.log('Użytkownik niezalogowany');
      }
    } catch (error) {
      console.error('Błąd podczas pobierania danych', error);
    }
  }

  deleteCookies(){
    this.cookieService.deleteAll();
    this.router.navigate(['login']);
  }
}
