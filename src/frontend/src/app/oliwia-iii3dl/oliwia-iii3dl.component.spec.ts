import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OliwiaIII3dlComponent } from './oliwia-iii3dl.component';

describe('OliwiaIII3dlComponent', () => {
  let component: OliwiaIII3dlComponent;
  let fixture: ComponentFixture<OliwiaIII3dlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OliwiaIII3dlComponent ]
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
