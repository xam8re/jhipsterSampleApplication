/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TaskOrderDetailComponent } from 'app/entities/task-order/task-order-detail.component';
import { TaskOrder } from 'app/shared/model/task-order.model';

describe('Component Tests', () => {
  describe('TaskOrder Management Detail Component', () => {
    let comp: TaskOrderDetailComponent;
    let fixture: ComponentFixture<TaskOrderDetailComponent>;
    const route = ({ data: of({ taskOrder: new TaskOrder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TaskOrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TaskOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
