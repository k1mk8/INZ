import { ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClientModule } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { Venus3DLComponent } from './venus3-dl.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('Venus3DLComponent', () => { 
  let component: Venus3DLComponent;
  let fixture: ComponentFixture<Venus3DLComponent>; 
  let httpTestingController: HttpTestingController;
  let cookieService: CookieService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [ Venus3DLComponent, MenuComponent, BottomBarComponent ],
      providers: [CookieService, Router],
    })
    .compileComponents();

    fixture = TestBed.createComponent(Venus3DLComponent); 
    component = fixture.componentInstance;
    httpTestingController = TestBed.inject(HttpTestingController);
    cookieService = TestBed.inject(CookieService);
    router = TestBed.inject(Router);

    spyOn(cookieService, 'get').and.returnValue('SESSION_TOKEN');
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should set name property to "Venus 3DL"', () => {
    expect(component.name).toEqual('Venus 3DL');
  });

  it('should fetch product availability', () => {
    const availabilityResponse = true;

    component.ngOnInit();

    const availabilityRequest = httpTestingController.expectOne('http://localhost:8082/checkAvailability');

    availabilityRequest.flush(availabilityResponse);

    expect(component.availability).toBe('Sprawdzanie dostepnosci');
  });
  
  it('should add product to the basket', () => {
    spyOn(component, 'openImageInNewWindow');

    const addProductData = {
      email: 'lukaszkonieczny@gmail.com',
      name: 'Venus 3DL',
      amount: 1,  
    };

    const cookieserviceSpy = spyOn(component.cookieservice, 'check').and.returnValue(true); 

    component.addToBasket();

    const addToBasketRequest = httpTestingController.expectOne('http://localhost:8082/addToBasket');
    addToBasketRequest.flush({}); 

    expect(cookieserviceSpy).toHaveBeenCalledWith('SESSION_TOKEN');
  });

  it('should open image in a new window', () => { 
    const openSpy = spyOn(window, 'open');
    component.openImageInNewWindow();
    expect(openSpy).toHaveBeenCalledWith('../../assets/venus3dl.jpg', '_blank');
  });

  it('should update tableVisible when window is scrolled', fakeAsync(() => {
    const table = document.createElement('table'); 
    table.id = 'myTable';
    document.body.appendChild(table);

    window.dispatchEvent(new Event('scroll')); 
    tick();

    expect(component.tableVisible).toBe(true); 

    document.body.removeChild(table); 
  }));

  afterEach(() => {
    httpTestingController.verify();
  });
});