import { ComponentFixture, TestBed} from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { PortfolioComponent } from './portfolio.component';
import { MenuComponent } from '../menu/menu.component';
import { BottomBarComponent } from '../bottom-bar/bottom-bar.component';
import { CookieService } from 'ngx-cookie-service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('PortfolioComponent', () => {
  let component: PortfolioComponent;
  let fixture: ComponentFixture<PortfolioComponent>;
  let httpTestingController: HttpTestingController; 

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule, HttpClientTestingModule,],
      declarations: [PortfolioComponent, MenuComponent, BottomBarComponent ],
      providers: [CookieService],
    }).compileComponents(); 

    fixture = TestBed.createComponent(PortfolioComponent);
    component = fixture.componentInstance;
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  afterEach(() => {
    fixture.destroy();
  });
});