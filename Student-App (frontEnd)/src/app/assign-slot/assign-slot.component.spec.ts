import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignSlotComponent } from './assign-slot.component';

describe('AssignSlotComponent', () => {
  let component: AssignSlotComponent;
  let fixture: ComponentFixture<AssignSlotComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AssignSlotComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AssignSlotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
