import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MenuComponent } from './menu.component';
import { HttpClientModule } from '@angular/common/http';

describe('MenuComponent', () => {
  let component: MenuComponent;
  let fixture: ComponentFixture<MenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
      declarations: [MenuComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy(); 
  });

  it('should have initialized rodzajeProduktow', () => {
    expect(component.rodzajeProduktow).toBeTruthy();
  });

  it('should add a product to the appropriate rodzaj', () => {
    component.addProduct('sofa', 'Sofa1');
    expect(component.rodzajeProduktow.sofa).toContain('Sofa1');
  });

  it('should add products to the appropriate rodzaj', () => {
    component.addProduct('sofa', 'Sofa2');
    component.addProduct('fotel', 'Fotel1');
    component.addProduct('sofa', 'Sofa3');
    component.addProduct('tapczan', 'Tapczan1');

    expect(component.rodzajeProduktow.sofa).toContain('Sofa2');
    expect(component.rodzajeProduktow.fotel).toContain('Fotel1');
    expect(component.rodzajeProduktow.tapczan).toContain('Tapczan1');
  });
});