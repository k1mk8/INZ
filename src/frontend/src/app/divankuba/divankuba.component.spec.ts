import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { DivankubaComponent } from './divankuba.component';

describe('DivankubaComponent', () => {
  let component: DivankubaComponent;
  let fixture: ComponentFixture<DivankubaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [ DivankubaComponent, MenuComponent, BottomBarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DivankubaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
