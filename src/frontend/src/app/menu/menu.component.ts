import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent {
  mobileOpen = false;
  isAdmin = false;

  constructor(private cookieService: CookieService) {
    try {
      this.isAdmin = this.cookieService.get('SESSION_ADMIN') === 'YES';
    } catch {
      this.isAdmin = false;
    }
  }

  toggleMobileMenu() {
    this.mobileOpen = !this.mobileOpen;
  }

  closeMobileMenu() {
    this.mobileOpen = false;
  }
}
