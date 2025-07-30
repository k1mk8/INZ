import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { MenuComponent } from '../menu/menu.component';
import { BottomBarComponent } from '../bottom-bar/bottom-bar.component';
import { TeamComponent } from './team.component';
import { CookieService } from 'ngx-cookie-service';

describe('TeamComponent', () => {
  let component: TeamComponent;
  let fixture: ComponentFixture<ProductTeamComponentsComponent>;
  let cookieService: CookieService;
  let httpClientSpy: { post: jasmine.Spy };

  beforeEach(async () => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['post']);

    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [TeamComponent, MenuComponent, BottomBarComponent,],
      providers: [
        CookieService,
        { provide: HttpClient, useValue: httpClientSpy },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(TeamComponent);
    component = fixture.componentInstance;
    cookieService = TestBed.inject(CookieService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
