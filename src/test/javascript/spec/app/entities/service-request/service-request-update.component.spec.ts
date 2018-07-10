/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ServiceRequestUpdateComponent } from 'app/entities/service-request/service-request-update.component';
import { ServiceRequestService } from 'app/entities/service-request/service-request.service';
import { ServiceRequest } from 'app/shared/model/service-request.model';

describe('Component Tests', () => {
  describe('ServiceRequest Management Update Component', () => {
    let comp: ServiceRequestUpdateComponent;
    let fixture: ComponentFixture<ServiceRequestUpdateComponent>;
    let service: ServiceRequestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ServiceRequestUpdateComponent]
      })
        .overrideTemplate(ServiceRequestUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceRequestUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceRequestService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new ServiceRequest(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.serviceRequest = entity;
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
          const entity = new ServiceRequest();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.serviceRequest = entity;
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
