import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DivankubaComponent } from './divankuba.component';

describe('DivankubaComponent', () => {
  let component: DivankubaComponent;
  let fixture: ComponentFixture<DivankubaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DivankubaComponent ]
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
