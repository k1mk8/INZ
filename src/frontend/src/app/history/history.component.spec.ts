import { ComponentFixture, TestBed, fakeAsync, tick} from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { HistoryComponent } from './history.component';
import { MenuComponent } from './../menu/menu.component';
import { ButtonsComponent } from './../buttons/buttons.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { CookieService } from 'ngx-cookie-service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('HistoryComponent', () => {
  let component: HistoryComponent;
  let fixture: ComponentFixture<HistoryComponent>;
  let httpTestingController: HttpTestingController; 

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule, HttpClientTestingModule,],
      declarations: [HistoryComponent, MenuComponent, BottomBarComponent, ButtonsComponent ],
      providers: [CookieService],
    }).compileComponents(); 

    fixture = TestBed.createComponent(HistoryComponent);
    component = fixture.componentInstance;
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch history on ngOnInit', fakeAsync(() => {
    const mockData = {
      id: ['1', '2'],
      state: ['ab', 'abc'],
      timestamp: ['123', '234']
    };

    component.ngOnInit();

    const req = httpTestingController.expectOne('http://localhost:8082/getOrders');
    req.flush(mockData); 

    tick(); 
    expect(component.id).toEqual([]); 
    expect(component.state).toEqual([]);
    expect(component.timestamp).toEqual([]);
  }));

  it('should fetch product from order', fakeAsync(() => {
    const mockData = {
      id: ['1', '2'],
      state: ['ab', 'abc'],
      timestamp: ['123', '234']
    };

    component.handleInnerSubscribe(3);

    const req = httpTestingController.expectOne('http://localhost:8082/getProductsFromOrder');
    req.flush(mockData); 

    tick(); 
    expect(component.name).toEqual([]); 
    expect(component.price).toEqual([]);
  }));

  afterEach(() => {
    fixture.destroy();
  });
});