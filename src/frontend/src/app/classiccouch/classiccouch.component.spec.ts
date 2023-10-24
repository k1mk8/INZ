import { ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { ClassiccouchComponent } from './classiccouch.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CookieService } from 'ngx-cookie-service';

describe('ClassiccouchComponent', () => {
  let component: ClassiccouchComponent;
  let fixture: ComponentFixture<ClassiccouchComponent>;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule, HttpClientTestingModule],
      declarations: [ ClassiccouchComponent, MenuComponent, BottomBarComponent ],
      providers: [CookieService],
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClassiccouchComponent);
    component = fixture.componentInstance;
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should create component', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch product availability', () => {
    const availabilityResponse = true;

    component.checkAvailability();

    const availabilityRequest = httpTestingController.expectOne('http://localhost:8082/checkAvailability');

    availabilityRequest.flush(availabilityResponse);

    expect(component.availability).toBe('Sprawdzanie dostepnosci');
    httpTestingController.verify();
  });

  it('should fetch product schedule', () => {  
    const availabilityResponse = true; 

    component.checkSchedule(); 

    const scheduleRequest = httpTestingController.expectOne('http://localhost:8082/checkSchedule');
    scheduleRequest.flush(availabilityResponse);
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
    addToBasketRequest.flush(addProductData); 
    

    expect(cookieserviceSpy).toHaveBeenCalledWith('SESSION_TOKEN');
  });

  it('should open image in a new window', () => {
    spyOn(window, 'open');

    component.openImageInNewWindow();

    expect(window.open).toHaveBeenCalledWith('../../assets/classiccouch.jpg', '_blank');
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
