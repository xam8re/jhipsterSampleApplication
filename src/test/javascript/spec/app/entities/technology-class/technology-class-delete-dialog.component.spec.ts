/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TechnologyClassDeleteDialogComponent } from 'app/entities/technology-class/technology-class-delete-dialog.component';
import { TechnologyClassService } from 'app/entities/technology-class/technology-class.service';

describe('Component Tests', () => {
  describe('TechnologyClass Management Delete Component', () => {
    let comp: TechnologyClassDeleteDialogComponent;
    let fixture: ComponentFixture<TechnologyClassDeleteDialogComponent>;
    let service: TechnologyClassService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TechnologyClassDeleteDialogComponent]
      })
        .overrideTemplate(TechnologyClassDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TechnologyClassDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TechnologyClassService);
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
