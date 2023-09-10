import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Venus3DLComponent } from './venus3-dl.component';

describe('Venus3DLComponent', () => {
  let component: Venus3DLComponent;
  let fixture: ComponentFixture<Venus3DLComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Venus3DLComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Venus3DLComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
