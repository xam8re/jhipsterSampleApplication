/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ServiceRequestClassDetailComponent } from 'app/entities/service-request-class/service-request-class-detail.component';
import { ServiceRequestClass } from 'app/shared/model/service-request-class.model';

describe('Component Tests', () => {
  describe('ServiceRequestClass Management Detail Component', () => {
    let comp: ServiceRequestClassDetailComponent;
    let fixture: ComponentFixture<ServiceRequestClassDetailComponent>;
    const route = ({ data: of({ serviceRequestClass: new ServiceRequestClass(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ServiceRequestClassDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceRequestClassDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceRequestClassDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceRequestClass).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
