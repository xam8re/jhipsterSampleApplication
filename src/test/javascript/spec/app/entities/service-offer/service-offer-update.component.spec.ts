/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ServiceOfferUpdateComponent } from 'app/entities/service-offer/service-offer-update.component';
import { ServiceOfferService } from 'app/entities/service-offer/service-offer.service';
import { ServiceOffer } from 'app/shared/model/service-offer.model';

describe('Component Tests', () => {
  describe('ServiceOffer Management Update Component', () => {
    let comp: ServiceOfferUpdateComponent;
    let fixture: ComponentFixture<ServiceOfferUpdateComponent>;
    let service: ServiceOfferService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ServiceOfferUpdateComponent]
      })
        .overrideTemplate(ServiceOfferUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceOfferUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceOfferService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new ServiceOffer(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.serviceOffer = entity;
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
          const entity = new ServiceOffer();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.serviceOffer = entity;
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
