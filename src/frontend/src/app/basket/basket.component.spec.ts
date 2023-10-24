import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MenuComponent } from './../menu/menu.component';
import { ButtonsComponent } from './../buttons/buttons.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { BasketComponent } from './basket.component';
import { CookieService } from 'ngx-cookie-service';
import { RouterTestingModule } from '@angular/router/testing';

describe('BasketComponent', () => {
  let component: BasketComponent;
  let fixture: ComponentFixture<BasketComponent>;
  let httpTestingController: HttpTestingController;
  
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule, HttpClientTestingModule],
      declarations: [ BasketComponent,  MenuComponent, BottomBarComponent, ButtonsComponent],
      providers: [CookieService],
    })
    .compileComponents();

    fixture = TestBed.createComponent(BasketComponent);
    component = fixture.componentInstance;
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the component', () => {
    const mockResponse = {
      id: 'testId',
      basketFinish: '2023-10-24',
    };

    const mockOrderResponse = [
      { name: 'Product 1', price: '10.00' },
      { name: 'Product 2', price: '20.00' },
    ];
    const client = { email: 'test@example.com' };
    component.ngOnInit();
    const clientReq = httpTestingController.expectOne('http://localhost:8082/getBasketOfClient');
    expect(clientReq.request.method).toBe('POST');
    clientReq.flush(mockResponse);

    expect(component.id).toBe(''); 
    expect(component.timestamp).toBe('');
    expect(component.name).toEqual([]);
    expect(component.price).toEqual([]);

    httpTestingController.verify();
  });

  it('should handle an empty basket', () => {
    const client = { email: 'lukaszkonieczny@gmail.com' };
    component.ngOnInit();
    const clientReq = httpTestingController.expectOne('http://localhost:8082/getBasketOfClient');
    expect(clientReq.request.method).toBe('POST'); 
    expect(clientReq.request.body).toEqual(client);
    clientReq.flush(client);

    expect(component.basket).toBe('');

    httpTestingController.verify();
  });

  it('should remove a product from the basket', () => {
    const productName = 'Product 1';

    component.removeFromBasket('Oliwia 3DL'); 
    const removeReq = httpTestingController.expectOne('http://localhost:8082/removeFromBasket');
    expect(removeReq.request.method).toBe('POST');
    removeReq.flush({}); 

    component.removeFromBasket(productName);
  });

  it('should place an order', () => { 
    const basket = { id: 'testId' };
    component.order();
    const orderReq = httpTestingController.expectOne('http://localhost:8082/order');
    expect(orderReq.request.method).toBe('POST');
    orderReq.flush({ message: 'Zamówienie zostało złożone:' });

    component.order();
  });

  afterEach(() => {
    fixture.destroy();
  });
});
