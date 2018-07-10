/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ServiceOrderDetailComponent } from 'app/entities/service-order/service-order-detail.component';
import { ServiceOrder } from 'app/shared/model/service-order.model';

describe('Component Tests', () => {
  describe('ServiceOrder Management Detail Component', () => {
    let comp: ServiceOrderDetailComponent;
    let fixture: ComponentFixture<ServiceOrderDetailComponent>;
    const route = ({ data: of({ serviceOrder: new ServiceOrder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ServiceOrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
