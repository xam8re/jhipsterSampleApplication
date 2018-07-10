/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ManufacturerDeleteDialogComponent } from 'app/entities/manufacturer/manufacturer-delete-dialog.component';
import { ManufacturerService } from 'app/entities/manufacturer/manufacturer.service';

describe('Component Tests', () => {
  describe('Manufacturer Management Delete Component', () => {
    let comp: ManufacturerDeleteDialogComponent;
    let fixture: ComponentFixture<ManufacturerDeleteDialogComponent>;
    let service: ManufacturerService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ManufacturerDeleteDialogComponent]
      })
        .overrideTemplate(ManufacturerDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ManufacturerDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ManufacturerService);
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
