/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TaskOfferUpdateComponent } from 'app/entities/task-offer/task-offer-update.component';
import { TaskOfferService } from 'app/entities/task-offer/task-offer.service';
import { TaskOffer } from 'app/shared/model/task-offer.model';

describe('Component Tests', () => {
  describe('TaskOffer Management Update Component', () => {
    let comp: TaskOfferUpdateComponent;
    let fixture: ComponentFixture<TaskOfferUpdateComponent>;
    let service: TaskOfferService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TaskOfferUpdateComponent]
      })
        .overrideTemplate(TaskOfferUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskOfferUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskOfferService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new TaskOffer(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.taskOffer = entity;
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
          const entity = new TaskOffer();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.taskOffer = entity;
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
