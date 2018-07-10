/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TaskOrderUpdateComponent } from 'app/entities/task-order/task-order-update.component';
import { TaskOrderService } from 'app/entities/task-order/task-order.service';
import { TaskOrder } from 'app/shared/model/task-order.model';

describe('Component Tests', () => {
  describe('TaskOrder Management Update Component', () => {
    let comp: TaskOrderUpdateComponent;
    let fixture: ComponentFixture<TaskOrderUpdateComponent>;
    let service: TaskOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TaskOrderUpdateComponent]
      })
        .overrideTemplate(TaskOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskOrderService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new TaskOrder(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.taskOrder = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );

      it(
        'Should call create service on save for new entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new TaskOrder();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.taskOrder = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );
    });
  });
});
