import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VenusFotelComponent } from './venus-fotel.component';

describe('VenusFotelComponent', () => {
  let component: VenusFotelComponent;
  let fixture: ComponentFixture<VenusFotelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VenusFotelComponent ]
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
