import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router, NavigationEnd } from '@angular/router';
import { Location } from '@angular/common';

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
  constructor(protected cookieService: CookieService, private router: Router, private location: Location) {
  }
  menuContent: any;
  name: string = "Venus 3DL";
  rodzajeProduktow: RodzajeProduktow = {
    sofa: ['Venus 3DL', 'Oliwia III 3DL'],
    fotel: ['Venus fotel', 'Matrix'],
    wersalka: ['Sara', 'Wersalka zwykła'],
    tapczan: ['Tapczan Kuba', 'Tapczan Jaś']
  };

  rodzaje = Object.keys(this.rodzajeProduktow);
  
  redirectToDetails(productId: string) {
    this.router.navigate(['/products/', productId]);
  }
}
