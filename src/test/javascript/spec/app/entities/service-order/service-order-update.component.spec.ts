/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ServiceOrderUpdateComponent } from 'app/entities/service-order/service-order-update.component';
import { ServiceOrderService } from 'app/entities/service-order/service-order.service';
import { ServiceOrder } from 'app/shared/model/service-order.model';

describe('Component Tests', () => {
  describe('ServiceOrder Management Update Component', () => {
    let comp: ServiceOrderUpdateComponent;
    let fixture: ComponentFixture<ServiceOrderUpdateComponent>;
    let service: ServiceOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ServiceOrderUpdateComponent]
      })
        .overrideTemplate(ServiceOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceOrderService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new ServiceOrder(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.serviceOrder = entity;
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
          const entity = new ServiceOrder();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.serviceOrder = entity;
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
