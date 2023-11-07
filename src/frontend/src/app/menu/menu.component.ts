import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router, NavigationEnd } from '@angular/router';
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
export class MenuComponent {
  constructor(protected cookieService: CookieService, private router: Router, private location: Location, private http: HttpClient) {
  }
  menuContent: any;
  name: string = "Venus 3DL";
  rodzajeProduktow: RodzajeProduktow = {
    sofa: [],
    fotel: [],
    wersalka: [],
    tapczan: []
  };

  rodzaje = Object.keys(this.rodzajeProduktow);

  async ngOnInit(): Promise<void> {
    try {
      const orderResponse: any = await this.http.get('http://localhost:8082/getProducts').toPromise();
        for (const value of orderResponse) {
          this.addProduct(value.type, value.name);
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
