/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TechnologyUpdateComponent } from 'app/entities/technology/technology-update.component';
import { TechnologyService } from 'app/entities/technology/technology.service';
import { Technology } from 'app/shared/model/technology.model';

describe('Component Tests', () => {
  describe('Technology Management Update Component', () => {
    let comp: TechnologyUpdateComponent;
    let fixture: ComponentFixture<TechnologyUpdateComponent>;
    let service: TechnologyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TechnologyUpdateComponent]
      })
        .overrideTemplate(TechnologyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TechnologyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TechnologyService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new Technology(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.technology = entity;
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
          const entity = new Technology();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.technology = entity;
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
