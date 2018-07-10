/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ServiceOfferDetailComponent } from 'app/entities/service-offer/service-offer-detail.component';
import { ServiceOffer } from 'app/shared/model/service-offer.model';

describe('Component Tests', () => {
  describe('ServiceOffer Management Detail Component', () => {
    let comp: ServiceOfferDetailComponent;
    let fixture: ComponentFixture<ServiceOfferDetailComponent>;
    const route = ({ data: of({ serviceOffer: new ServiceOffer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ServiceOfferDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceOfferDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceOfferDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceOffer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
