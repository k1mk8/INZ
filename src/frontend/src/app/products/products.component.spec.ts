import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { of } from 'rxjs';
import { ProductsComponent } from './products.component';
import { CookieService } from 'ngx-cookie-service';

describe('ProductsComponent', () => {
  let component: ProductsComponent;
  let fixture: ComponentFixture<ProductsComponent>;
  let cookieService: CookieService;
  let httpClientSpy: { post: jasmine.Spy };

  beforeEach(async () => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['post']);

    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ProductsComponent, MenuComponent, BottomBarComponent,],
      providers: [
        CookieService,
        { provide: HttpClient, useValue: httpClientSpy },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductsComponent);
    component = fixture.componentInstance;
    cookieService = TestBed.inject(CookieService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get product details', async () => {
    const mockAvailabilityResponse = {
      price: '100',
      description: 'Test description',
      dimension: '10x10x10',
    };
    httpClientSpy.post.and.returnValue(of(mockAvailabilityResponse));

    component.name = 'TestProduct';
    await component.getProductsData();

    expect(component.price).toEqual('100');
    expect(component.description).toEqual('Test description');
    expect(component.space).toEqual('10x10x10');
  });

  it('should check product availability', async () => {
    httpClientSpy.post.and.returnValue(of(true));

    component.name = 'TestProduct';
    await component.checkAvailability();

    expect(component.availability).toEqual('Produkt dostępny');
  });

  it('should check product schedule', async () => {
    httpClientSpy.post.and.returnValue(of({ date: '2023-12-31' }));

    component.name = 'TestProduct';
    await component.checkSchedule(); 

    expect(component.timing).toEqual('2023-12-31');
  });

  it('should add product to the basket', async () => {
    cookieService.set('SESSION_TOKEN', 'john@example.com');
    httpClientSpy.post.and.returnValue(of({}));

    spyOn(component['router'], 'navigate');
    await component.addToBasket();

    expect(httpClientSpy.post).toHaveBeenCalledWith('http://localhost:8082/addToBasket', {
      email: 'john@example.com',
      name: component.name,
      amount: 1,
    });

    expect(component['router'].navigate).toHaveBeenCalledWith(['basket']);  
  });

  it('should not add product to the basket and navigate to login if not logged in', async () => {
    cookieService.delete('SESSION_TOKEN');
    spyOn(component['router'], 'navigate');

    await component.addToBasket(); 

    expect(component['router'].navigate).toHaveBeenCalledWith(['login']);
  });
});
