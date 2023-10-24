import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { OliwiaIII3dlComponent } from './oliwia-iii3dl.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('OliwiaIII3dlComponent', () => {
  let component: OliwiaIII3dlComponent;
  let fixture: ComponentFixture<OliwiaIII3dlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ OliwiaIII3dlComponent, MenuComponent, BottomBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OliwiaIII3dlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
