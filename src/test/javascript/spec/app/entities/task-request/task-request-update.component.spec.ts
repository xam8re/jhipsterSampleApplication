/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TaskRequestUpdateComponent } from 'app/entities/task-request/task-request-update.component';
import { TaskRequestService } from 'app/entities/task-request/task-request.service';
import { TaskRequest } from 'app/shared/model/task-request.model';

describe('Component Tests', () => {
  describe('TaskRequest Management Update Component', () => {
    let comp: TaskRequestUpdateComponent;
    let fixture: ComponentFixture<TaskRequestUpdateComponent>;
    let service: TaskRequestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TaskRequestUpdateComponent]
      })
        .overrideTemplate(TaskRequestUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskRequestUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskRequestService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new TaskRequest(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.taskRequest = entity;
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
          const entity = new TaskRequest();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.taskRequest = entity;
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
