/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { MachineModelDetailComponent } from 'app/entities/machine-model/machine-model-detail.component';
import { MachineModel } from 'app/shared/model/machine-model.model';

describe('Component Tests', () => {
  describe('MachineModel Management Detail Component', () => {
    let comp: MachineModelDetailComponent;
    let fixture: ComponentFixture<MachineModelDetailComponent>;
    const route = ({ data: of({ machineModel: new MachineModel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [MachineModelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MachineModelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MachineModelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.machineModel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
