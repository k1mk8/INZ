import { ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClientModule } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { SaraComponent } from './sara.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('SaraComponent', () => {
  let component: SaraComponent;
  let fixture: ComponentFixture<SaraComponent>;
  let httpTestingController: HttpTestingController;
  let cookieService: CookieService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule, HttpClientTestingModule],
      declarations: [ SaraComponent, MenuComponent, BottomBarComponent ],
      providers: [CookieService, Router],
    })
    .compileComponents();

    fixture = TestBed.createComponent(SaraComponent);
    component = fixture.componentInstance;
    httpTestingController = TestBed.inject(HttpTestingController);
    cookieService = TestBed.inject(CookieService);
    router = TestBed.inject(Router);

    spyOn(cookieService, 'get').and.returnValue('SESSION_TOKEN');
  });

  it('should create component', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch product availability', () => {
    const productData = { name: 'Sara' };
    const availabilityResponse = true;

    component.checkAvailability();

    const availabilityRequest = httpTestingController.expectOne('http://localhost:8082/checkAvailability');

    availabilityRequest.flush(availabilityResponse);

    expect(component.availability).toBe('Sprawdzanie dostepnosci');
  });

  it('should fetch product schedule', () => {
    const productData = { name: 'Sara' };
    const availabilityResponse = true; 

    component.checkSchedule();

    const availabilityRequest = httpTestingController.expectOne('http://localhost:8082/checkSchedule');
  });
  
  it('should add product to the basket', () => {
    spyOn(component, 'openImageInNewWindow');

    const addProductData = {
      email: 'lukaszkonieczny@gmail.com',
      name: 'Wersalka zwykła', 
      amount: 1,  
    };

    const cookieserviceSpy = spyOn(component.cookieservice, 'check').and.returnValue(true); 

    component.addToBasket();

    const addToBasketRequest = httpTestingController.expectOne('http://localhost:8082/addToBasket');
    addToBasketRequest.flush({}); 
    

    expect(cookieserviceSpy).toHaveBeenCalledWith('SESSION_TOKEN');
  });

  it('should open image in a new window', () => {
    spyOn(window, 'open');

    component.openImageInNewWindow();

    expect(window.open).toHaveBeenCalledWith('../../assets/sara.jpg', '_blank');
  });

  it('should update tableVisible when window is scrolled', fakeAsync(() => {
    const table = document.createElement('table');
    table.id = 'myTable';
    document.body.appendChild(table);

    window.dispatchEvent(new Event('scroll'));  
    tick();
    component.tableVisible = true;
    expect(component.tableVisible).toBe(true); 

    document.body.removeChild(table);
  })); 

  afterEach(() => {
    httpTestingController.verify();
  });
});
