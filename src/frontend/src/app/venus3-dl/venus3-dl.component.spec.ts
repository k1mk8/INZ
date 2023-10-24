import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { MenuComponent } from './../menu/menu.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { Venus3DLComponent } from './venus3-dl.component';
import { CookieService } from 'ngx-cookie-service';
import { RouterTestingModule } from '@angular/router/testing';

describe('Venus3DLComponent', () => { 
  let component: Venus3DLComponent;
  let fixture: ComponentFixture<Venus3DLComponent>; 

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [ Venus3DLComponent, MenuComponent, BottomBarComponent ],
      providers: [CookieService],
    })
    .compileComponents();

    fixture = TestBed.createComponent(Venus3DLComponent); 
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should set name property to "Venus 3DL"', () => {
    expect(component.name).toEqual('Venus 3DL');
  });

  it('should initialize availability to "Sprawdzanie dostepnosci"', () => {
    expect(component.availability).toEqual('Sprawdzanie dostepnosci');
  });

  it('should open image in a new window', () => { 
    const openSpy = spyOn(window, 'open');
    component.openImageInNewWindow();
    expect(openSpy).toHaveBeenCalledWith('../../assets/venus3dl.jpg', '_blank');
  });
});