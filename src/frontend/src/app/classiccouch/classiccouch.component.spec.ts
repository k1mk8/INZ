import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassiccouchComponent } from './classiccouch.component';

describe('ClassiccouchComponent', () => {
  let component: ClassiccouchComponent;
  let fixture: ComponentFixture<ClassiccouchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClassiccouchComponent ]
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
