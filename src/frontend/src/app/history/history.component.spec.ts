import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { MenuComponent } from './../menu/menu.component';
import { ButtonsComponent } from './../buttons/buttons.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { HistoryComponent } from './history.component';
import { RouterTestingModule } from '@angular/router/testing';


describe('HistoryComponent', () => {
  let component: HistoryComponent;
  let fixture: ComponentFixture<HistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ HistoryComponent, MenuComponent, BottomBarComponent, ButtonsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
