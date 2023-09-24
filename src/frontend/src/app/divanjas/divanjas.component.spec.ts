import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DivanjasComponent } from './divanjas.component';

describe('DivanjasComponent', () => {
  let component: DivanjasComponent;
  let fixture: ComponentFixture<DivanjasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DivanjasComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DivanjasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
