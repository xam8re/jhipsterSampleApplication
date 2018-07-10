/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AMSAUserDeleteDialogComponent } from 'app/entities/amsa-user/amsa-user-delete-dialog.component';
import { AMSAUserService } from 'app/entities/amsa-user/amsa-user.service';

describe('Component Tests', () => {
  describe('AMSAUser Management Delete Component', () => {
    let comp: AMSAUserDeleteDialogComponent;
    let fixture: ComponentFixture<AMSAUserDeleteDialogComponent>;
    let service: AMSAUserService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [AMSAUserDeleteDialogComponent]
      })
        .overrideTemplate(AMSAUserDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AMSAUserDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AMSAUserService);
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
