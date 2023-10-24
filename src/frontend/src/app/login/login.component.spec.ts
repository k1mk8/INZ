import { ComponentFixture, TestBed, fakeAsync, tick  } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController  } from '@angular/common/http/testing';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { LoginComponent } from './login.component';
import { HttpClient } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { FormsModule } from '@angular/forms'; 
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { of } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let httpClient: HttpClient;
  let cookieService: CookieService;
  let router: Router;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule, FormsModule, HttpClientTestingModule],
      declarations: [ LoginComponent,  MenuComponent, BottomBarComponent ],
      providers: [CookieService]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    httpClient = TestBed.inject(HttpClient); 
    cookieService = TestBed.inject(CookieService);
    router = TestBed.inject(Router);
  });

  it('should create component', () => { 
    expect(component).toBeTruthy();
  });

  it('should log in successfully', () => {
    const userData = {
      email: 'lukaszkonieczny@gmail.com',
      password: 'password'
    };

    const expectedResponse = true;

    component.email = userData.email;
    component.password = userData.password;
  });

  it('should handle invalid login', fakeAsync(() => {
    const userData = {
      email: 'test@example.com',
      password: 'incorrect'
    };

    spyOn(httpClient, 'post').and.returnValue(of(false));

    component.email = userData.email;
    component.password = userData.password; 

    component.login();

    tick();
    expect(component.message).toBe('Nieprawidłowy email lub hasło');
  }));
});
