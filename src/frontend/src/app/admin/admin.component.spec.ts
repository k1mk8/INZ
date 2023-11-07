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

  it('should initialize type and name arrays', () => {
    expect(component.type).toEqual([]);
    expect(component.name).toEqual([]); 
  });

  it('should fetch products on ngOnInit', fakeAsync(() => {
    const mockData = [
      { type: 'sofa', name: 'Sofa1' },
      { type: 'fotel', name: 'Fotel1' },
    ];

    component.ngOnInit();

    const req = httpTestingController.expectOne('http://localhost:8082/getProducts');
    req.flush(mockData);

    tick();
    expect(component.type).toEqual(['sofa', 'fotel']); 
    expect(component.name).toEqual(['Sofa1', 'Fotel1']);
  }));

  it('should remove a product from type and name arrays in removeProduct', async () => { 
    component.type = ['Type1', 'Type2']; 
    component.name = ['Product1', 'Product2']; 
  
    
    const httpPostSpy = spyOn(component['http'], 'post').and.returnValue(of(true));
    await component.removeProduct('Product1');
  
    httpPostSpy.calls.mostRecent().returnValue.subscribe(() => {
      expect(component.type).not.toContain('Type1');
      expect(component.name).not.toContain('Product1');  
    });
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