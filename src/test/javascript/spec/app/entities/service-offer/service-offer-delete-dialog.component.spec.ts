/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ServiceOfferDeleteDialogComponent } from 'app/entities/service-offer/service-offer-delete-dialog.component';
import { ServiceOfferService } from 'app/entities/service-offer/service-offer.service';

describe('Component Tests', () => {
  describe('ServiceOffer Management Delete Component', () => {
    let comp: ServiceOfferDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceOfferDeleteDialogComponent>;
    let service: ServiceOfferService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ServiceOfferDeleteDialogComponent]
      })
        .overrideTemplate(ServiceOfferDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceOfferDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceOfferService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
