import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent {
  constructor(private cookieService: CookieService, private http: HttpClient, private router: Router) {}

  name: string = '';
  surname: string = '';
  number: string = '';
  email: string = '';

  ngOnInit() {
    this.email = this.cookieService.get('SESSION_TOKEN');
    this.http.post('http://localhost:8082/clientByEmail', this.email).subscribe(
      (response: any) => {
        if (response != null)
        {
          console.log('Użytkownik zalogowany', response);
          this.name = response.name;
          this.surname = response.surname;
          this.number = response.number;
        }
        else
        {
          console.log('Użytkownik niezalogowany');
        }
      },
      (error) => {
        console.error('Błąd podczas pobierania danych', error);
      }
    );
  }

  deleteCookies(){
    this.cookieService.deleteAll();
    this.router.navigate(['login']);
  }
}
