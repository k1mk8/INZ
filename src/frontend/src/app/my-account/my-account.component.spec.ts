import { ComponentFixture, TestBed, async, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CookieService } from 'ngx-cookie-service';
import { MenuComponent } from './../menu/menu.component';
import { ButtonsComponent } from './../buttons/buttons.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { MyAccountComponent } from './my-account.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('MyAccountComponent', () => {
  let component: MyAccountComponent;
  let fixture: ComponentFixture<MyAccountComponent>;
  let cookieService: CookieService;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule, HttpClientTestingModule],
      declarations: [ MyAccountComponent, MenuComponent, BottomBarComponent, ButtonsComponent ],
      providers: [CookieService],
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyAccountComponent);
    component = fixture.componentInstance;
    cookieService = TestBed.inject(CookieService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize user data from server', fakeAsync(() => {
    // Stub the cookie value
    const mockEmail = 'lukaszkonieczny@gmail.com';
    spyOn(cookieService, 'get').and.returnValue(mockEmail);

    // Mock the HTTP request
    const mockUserData = {
      name: 'Lukasz',
      surname: 'Konieczny', 
      number: '+48223456789',
    };
    component.ngOnInit();
    const request = httpTestingController.expectOne('http://localhost:8082/clientByEmail');
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual(mockEmail);
    request.flush(mockUserData);

    fixture.detectChanges();
    tick();

    expect(component.email).toEqual(mockEmail); 
    expect(component.name).toEqual(mockUserData.name);
    expect(component.surname).toEqual(mockUserData.surname);
    expect(component.number).toEqual(mockUserData.number);
    const request2 = httpTestingController.expectOne('http://localhost:8082/clientByEmail');
    httpTestingController.verify();
  }));

  it('should handle errors during data retrieval', fakeAsync(() => {
    const mockEmail = 'test@example.com';
    spyOn(cookieService, 'get').and.returnValue(mockEmail); 

    component.ngOnInit();
    const errorMessage = 'Błąd podczas pobierania danych';
    const request = httpTestingController.expectOne('http://localhost:8082/clientByEmail');
    expect(request.request.method).toBe('POST');
    request.flush(errorMessage, { status: 500, statusText: 'Internal Server Error' });

    fixture.detectChanges();
    tick();

    expect(component.email).toEqual(mockEmail); 
    expect(component.name).toEqual('');
    expect(component.surname).toEqual('');
    expect(component.number).toEqual('');
    const request2 = httpTestingController.expectOne('http://localhost:8082/clientByEmail');
    httpTestingController.verify();
  }));

  it('should delete cookies and navigate to login page', () => {
    spyOn(cookieService, 'deleteAll').and.callThrough();
    spyOn(component.router, 'navigate');

    component.deleteCookies();

    expect(cookieService.deleteAll).toHaveBeenCalled();
    expect(component.router.navigate).toHaveBeenCalledWith(['login']);
  });
});
