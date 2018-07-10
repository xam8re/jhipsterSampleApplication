/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { MachineModelUpdateComponent } from 'app/entities/machine-model/machine-model-update.component';
import { MachineModelService } from 'app/entities/machine-model/machine-model.service';
import { MachineModel } from 'app/shared/model/machine-model.model';

describe('Component Tests', () => {
  describe('MachineModel Management Update Component', () => {
    let comp: MachineModelUpdateComponent;
    let fixture: ComponentFixture<MachineModelUpdateComponent>;
    let service: MachineModelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [MachineModelUpdateComponent]
      })
        .overrideTemplate(MachineModelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MachineModelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MachineModelService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new MachineModel(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.machineModel = entity;
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
          const entity = new MachineModel();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.machineModel = entity;
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
