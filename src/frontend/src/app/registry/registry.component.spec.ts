import { ComponentFixture, TestBed, async, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { RegistryComponent } from './registry.component';
import { RouterTestingModule } from '@angular/router/testing';
import { FormsModule } from '@angular/forms'; 
import { By } from '@angular/platform-browser';

describe('RegistryComponent', () => {
  let component: RegistryComponent;
  let fixture: ComponentFixture<RegistryComponent>;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule, FormsModule, HttpClientTestingModule],
      declarations: [ RegistryComponent, MenuComponent, BottomBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistryComponent);
    component = fixture.componentInstance;
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should successfully register a new user', fakeAsync(() => {
    const userData = {
      email: 'test@example.com',
      password: 'password123',
      name: 'John',
      surname: 'Doe',
      number: '12345',
    }; 
  }));

  it('should handle registration error', fakeAsync(() => {
    const userData = {
      email: 'test@example.com',
      password: 'password123',
      name: 'John',
      surname: 'Doe',
      number: '12345',
    };

    // Mock the HTTP request to simulate an error
    const errorMessage = 'User with the same email already exists';
    component.register(); 
    const request = httpTestingController.expectOne('http://localhost:8082/register');
    expect(request.request.method).toBe('POST');
    request.flush(errorMessage, { status: 400, statusText: 'Bad Request' });

    // Perform user registration

    expect(component.message).toEqual('');

  }));

  it('should handle registration failure', fakeAsync(() => {
    // Mock user registration data
    const userData = {
      email: 'test@example.com',
      password: 'password123',
      name: 'John',
      surname: 'Doe',
      number: '12345',
    };

    // Mock the HTTP request to simulate a failure
    const errorMessage = 'Server error';
    component.register();
    const request = httpTestingController.expectOne('http://localhost:8082/register');
    expect(request.request.method).toBe('POST');
    request.error(new ErrorEvent('HttpErrorResponse', { error: errorMessage }));


    expect(component.message).toEqual('');
  }));

  afterEach(() => {
    httpTestingController.verify();
  });
});
