/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TaskOfferDetailComponent } from 'app/entities/task-offer/task-offer-detail.component';
import { TaskOffer } from 'app/shared/model/task-offer.model';

describe('Component Tests', () => {
  describe('TaskOffer Management Detail Component', () => {
    let comp: TaskOfferDetailComponent;
    let fixture: ComponentFixture<TaskOfferDetailComponent>;
    const route = ({ data: of({ taskOffer: new TaskOffer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TaskOfferDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TaskOfferDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskOfferDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskOffer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
