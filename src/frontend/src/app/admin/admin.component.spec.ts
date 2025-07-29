import { ComponentFixture, TestBed, waitForAsync, fakeAsync, tick } from '@angular/core/testing';
import { AdminComponent } from './admin.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { CookieService } from 'ngx-cookie-service';
import { of } from 'rxjs';
import { Router } from '@angular/router';


describe('AdminComponent', () => {
  let component: AdminComponent;
  let fixture: ComponentFixture<AdminComponent>; 
  let httpTestingController: HttpTestingController; 

  beforeEach(waitForAsync(() => { 
    TestBed.configureTestingModule({
      imports: [HttpClientModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [AdminComponent, MenuComponent, BottomBarComponent,],
      providers: [CookieService, Router, HttpClient],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminComponent);
    component = fixture.componentInstance;
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should delete all cookies and navigate to login page in deleteCookies', () => {
    const cookieService = TestBed.inject(CookieService);
    spyOn(cookieService, 'deleteAll');
    spyOn(component['router'], 'navigate');

    component.deleteCookies();

    expect(cookieService.deleteAll).toHaveBeenCalled();
    expect(component['router'].navigate).toHaveBeenCalledWith(['login']);
  });
});