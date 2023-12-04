import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router} from '@angular/router';
import { Location } from '@angular/common';
import { HttpClient } from '@angular/common/http';

interface RodzajeProduktow {
  sofa: string[];
  fotel: string[];
  wersalka: string[];
  tapczan: string[];
}
@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  constructor(protected cookieService: CookieService, private router: Router, private location: Location, private http: HttpClient) {
  }
  rodzajeProduktow: RodzajeProduktow = {
    sofa: [],
    fotel: [],
    wersalka: [],
    tapczan: []
  };

  rodzaje = Object.keys(this.rodzajeProduktow);

  async ngOnInit(): Promise<void> {
    try {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const orderResponse: any = await this.http.get('http://localhost:8082/getProducts').toPromise();
        for (const value of orderResponse) {
          if(value.is_active === "t"){
            this.addProduct(value.type, value.name);
          }
        } 
    } catch (error) {
      console.error('Błąd podczas pobierania produktów', error);
    }
  }

  addProduct(type: string, name: string) {
    if (type === 'sofa'){
      this.rodzajeProduktow.sofa.push(name);
    }
    else if (type === 'fotel'){
      this.rodzajeProduktow.fotel.push(name);
    }
    else if (type === 'wersalka'){
      this.rodzajeProduktow.wersalka.push(name);
    }
    else if (type === 'tapczan'){
      this.rodzajeProduktow.tapczan.push(name);
    }

  }

  redirectToDetails(productId: string) {
    this.router.navigate(['/products/', productId]);
  }
}
