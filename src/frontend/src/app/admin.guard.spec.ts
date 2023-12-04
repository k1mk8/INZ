import { AdminGuard } from './admin.guard';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

describe('AdminGuard', () => {
  let guard: AdminGuard;
  let router: Router;
  let cookieService: CookieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      providers: [AdminGuard, CookieService],
    });
    guard = TestBed.inject(AdminGuard);
    router = TestBed.inject(Router);
    cookieService = TestBed.inject(CookieService);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should allow access for admin', () => {
    spyOn(cookieService, 'get').and.returnValue('YES');

    const canActivate = guard.canActivate();

    expect(canActivate).toBe(true);
  });

  it('should deny access for non-admin', () => {
    spyOn(cookieService, 'get').and.returnValue('NO');
    const navigateSpy = spyOn(router, 'navigate');

    const canActivate = guard.canActivate();

    expect(canActivate).toBe(false);
    expect(navigateSpy).toHaveBeenCalledWith(['/login']);
  });
});