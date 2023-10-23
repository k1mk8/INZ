import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { ClassiccouchComponent } from './classiccouch.component';

describe('ClassiccouchComponent', () => {
  let component: ClassiccouchComponent;
  let fixture: ComponentFixture<ClassiccouchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [ ClassiccouchComponent, MenuComponent, BottomBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClassiccouchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
