import { ComponentFixture, TestBed, waitForAsync, fakeAsync, tick } from '@angular/core/testing';
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
  let httpTestingController: HttpTestingController;

  beforeEach(waitForAsync(() => {
      TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule, HttpClientTestingModule],
      declarations: [ MyAccountComponent, MenuComponent, BottomBarComponent, ButtonsComponent ],
      providers: [CookieService],
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyAccountComponent);
    component = fixture.componentInstance;
    httpTestingController = TestBed.inject(HttpTestingController);
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch products on ngOnInit', fakeAsync(() => {
    const mockData = {
      name: 'Karol',
      surname: 'Kasperek',
      email: 'kasperkk@gmail.com',
      number: '+45123123'
    };

    component.ngOnInit();

    const req = httpTestingController.expectOne('http://localhost:8082/clientByEmail');
    req.flush(mockData);

    tick();
    expect(component.name).toEqual('Karol'); 
    expect(component.surname).toEqual('Kasperek');
  }));

  it('should delete all cookies and navigate to login page in deleteCookies', () => {
    const cookieService = TestBed.inject(CookieService);
    spyOn(cookieService, 'deleteAll');
    spyOn(component['router'], 'navigate');

    component.deleteCookies();  

    expect(cookieService.deleteAll).toHaveBeenCalled();
    expect(component['router'].navigate).toHaveBeenCalledWith(['login']);
  });
});
