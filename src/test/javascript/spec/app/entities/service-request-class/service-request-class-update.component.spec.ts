/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ServiceRequestClassUpdateComponent } from 'app/entities/service-request-class/service-request-class-update.component';
import { ServiceRequestClassService } from 'app/entities/service-request-class/service-request-class.service';
import { ServiceRequestClass } from 'app/shared/model/service-request-class.model';

describe('Component Tests', () => {
  describe('ServiceRequestClass Management Update Component', () => {
    let comp: ServiceRequestClassUpdateComponent;
    let fixture: ComponentFixture<ServiceRequestClassUpdateComponent>;
    let service: ServiceRequestClassService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ServiceRequestClassUpdateComponent]
      })
        .overrideTemplate(ServiceRequestClassUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceRequestClassUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceRequestClassService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new ServiceRequestClass(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.serviceRequestClass = entity;
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
          const entity = new ServiceRequestClass();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.serviceRequestClass = entity;
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
