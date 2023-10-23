import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { VenusFotelComponent } from './venus-fotel.component';

describe('VenusFotelComponent', () => {
  let component: VenusFotelComponent;
  let fixture: ComponentFixture<VenusFotelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [ VenusFotelComponent, MenuComponent, BottomBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VenusFotelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
