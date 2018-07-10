/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ManufacturerUpdateComponent } from 'app/entities/manufacturer/manufacturer-update.component';
import { ManufacturerService } from 'app/entities/manufacturer/manufacturer.service';
import { Manufacturer } from 'app/shared/model/manufacturer.model';

describe('Component Tests', () => {
  describe('Manufacturer Management Update Component', () => {
    let comp: ManufacturerUpdateComponent;
    let fixture: ComponentFixture<ManufacturerUpdateComponent>;
    let service: ManufacturerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ManufacturerUpdateComponent]
      })
        .overrideTemplate(ManufacturerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ManufacturerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ManufacturerService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new Manufacturer(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.manufacturer = entity;
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
          const entity = new Manufacturer();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.manufacturer = entity;
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
