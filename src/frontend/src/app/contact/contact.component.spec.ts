import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MenuComponent } from './../menu/menu.component';
import { BottomBarComponent } from './../bottom-bar/bottom-bar.component';
import { ContactComponent } from './contact.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('ContactComponent', () => {
  let component: ContactComponent;
  let fixture: ComponentFixture<ContactComponent>;
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule],
      declarations: [ ContactComponent, MenuComponent, BottomBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContactComponent);
    component = fixture.componentInstance;
    httpTestingController = TestBed.inject(HttpTestingController);
    fixture.detectChanges();
  });

  it('should create component', () => {
    expect(component).toBeTruthy();
  });

  it('should render contact information', () => {
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('.name').textContent).toContain('STOLMEL');
    expect(compiled.querySelector('.email').textContent).toContain('E-mail:');
    expect(compiled.querySelector('.value-email').textContent).toContain('stolmel@wp.pl');
    expect(compiled.querySelector('.telefon').textContent).toContain('Telefon:');
    expect(compiled.querySelector('.value-telefon').textContent).toContain('81 307 04 92');
    expect(compiled.querySelector('.adres').textContent).toContain('Adres:');
    expect(compiled.querySelector('.value-adres').textContent).toContain('Stara wieś 29, 21-013 Puchaczów');
  });

  it('should render Google Maps iframe', () => {
    const compiled = fixture.nativeElement;
    const iframe = compiled.querySelector('iframe');
    expect(iframe).toBeTruthy();
    expect(iframe.getAttribute('src')).toContain('maps.google.com');
  });
});
