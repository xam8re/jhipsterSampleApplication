/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AMSAUserDetailComponent } from 'app/entities/amsa-user/amsa-user-detail.component';
import { AMSAUser } from 'app/shared/model/amsa-user.model';

describe('Component Tests', () => {
  describe('AMSAUser Management Detail Component', () => {
    let comp: AMSAUserDetailComponent;
    let fixture: ComponentFixture<AMSAUserDetailComponent>;
    const route = ({ data: of({ aMSAUser: new AMSAUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [AMSAUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AMSAUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AMSAUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aMSAUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
