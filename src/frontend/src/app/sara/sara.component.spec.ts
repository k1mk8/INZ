import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { SaraComponent } from './sara.component';

describe('SaraComponent', () => {
  let component: SaraComponent;
  let fixture: ComponentFixture<SaraComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ SaraComponent, MenuComponent, BottomBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SaraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
