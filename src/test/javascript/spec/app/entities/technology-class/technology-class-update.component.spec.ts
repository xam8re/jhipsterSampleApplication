/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TechnologyClassUpdateComponent } from 'app/entities/technology-class/technology-class-update.component';
import { TechnologyClassService } from 'app/entities/technology-class/technology-class.service';
import { TechnologyClass } from 'app/shared/model/technology-class.model';

describe('Component Tests', () => {
  describe('TechnologyClass Management Update Component', () => {
    let comp: TechnologyClassUpdateComponent;
    let fixture: ComponentFixture<TechnologyClassUpdateComponent>;
    let service: TechnologyClassService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TechnologyClassUpdateComponent]
      })
        .overrideTemplate(TechnologyClassUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TechnologyClassUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TechnologyClassService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new TechnologyClass(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.technologyClass = entity;
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
          const entity = new TechnologyClass();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.technologyClass = entity;
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
