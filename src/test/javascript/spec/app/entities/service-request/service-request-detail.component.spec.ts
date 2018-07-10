/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ServiceRequestDetailComponent } from 'app/entities/service-request/service-request-detail.component';
import { ServiceRequest } from 'app/shared/model/service-request.model';

describe('Component Tests', () => {
  describe('ServiceRequest Management Detail Component', () => {
    let comp: ServiceRequestDetailComponent;
    let fixture: ComponentFixture<ServiceRequestDetailComponent>;
    const route = ({ data: of({ serviceRequest: new ServiceRequest(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ServiceRequestDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceRequestDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceRequestDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceRequest).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
