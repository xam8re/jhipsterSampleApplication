/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AMSAUserUpdateComponent } from 'app/entities/amsa-user/amsa-user-update.component';
import { AMSAUserService } from 'app/entities/amsa-user/amsa-user.service';
import { AMSAUser } from 'app/shared/model/amsa-user.model';

describe('Component Tests', () => {
  describe('AMSAUser Management Update Component', () => {
    let comp: AMSAUserUpdateComponent;
    let fixture: ComponentFixture<AMSAUserUpdateComponent>;
    let service: AMSAUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [AMSAUserUpdateComponent]
      })
        .overrideTemplate(AMSAUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AMSAUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AMSAUserService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new AMSAUser(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.aMSAUser = entity;
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
          const entity = new AMSAUser();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.aMSAUser = entity;
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
