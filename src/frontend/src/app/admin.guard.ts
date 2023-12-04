import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private cookieService: CookieService, private router: Router) {}

  canActivate(
  ): boolean | UrlTree {
    // Odczytaj status admina z ciasteczka
    const isAdmin = this.cookieService.get('SESSION_ADMIN');

    if (isAdmin === 'YES') {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}