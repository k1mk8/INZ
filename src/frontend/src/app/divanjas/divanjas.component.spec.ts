import { ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { DivanjasComponent } from './divanjas.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('DivanjasComponent', () => {
  let component: DivanjasComponent;
  let fixture: ComponentFixture<DivanjasComponent>;
  let httpTestingController: HttpTestingController;
  let cookieService: CookieService;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DivanjasComponent, MenuComponent, BottomBarComponent],
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [CookieService, Router],
    });

    fixture = TestBed.createComponent(DivanjasComponent);
    component = fixture.componentInstance;
    httpTestingController = TestBed.inject(HttpTestingController);
    cookieService = TestBed.inject(CookieService);
    router = TestBed.inject(Router);

    // Mock some necessary methods or properties from the router or cookie service.
    spyOn(cookieService, 'check').and.returnValue(true);
    spyOn(cookieService, 'get').and.returnValue('SESSION_TOKEN');
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should update tableVisible when window is scrolled', fakeAsync(() => {
    const table = document.createElement('table');
    table.id = 'myTable';
    document.body.appendChild(table);

    // Simulate window scroll event
    window.dispatchEvent(new Event('scroll'));
    tick();

    expect(component.tableVisible).toBe(true);

    document.body.removeChild(table);
  }));

  afterEach(() => {
    httpTestingController.verify();
  });
});