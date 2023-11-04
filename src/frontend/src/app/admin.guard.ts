import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private cookieService: CookieService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
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