import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { MainPageComponent } from './main-page.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('MainPageComponent', () => {
  let component: MainPageComponent;
  let fixture: ComponentFixture<MainPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [ MainPageComponent, MenuComponent, BottomBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MainPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
