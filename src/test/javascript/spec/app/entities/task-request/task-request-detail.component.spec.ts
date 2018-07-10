/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TaskRequestDetailComponent } from 'app/entities/task-request/task-request-detail.component';
import { TaskRequest } from 'app/shared/model/task-request.model';

describe('Component Tests', () => {
  describe('TaskRequest Management Detail Component', () => {
    let comp: TaskRequestDetailComponent;
    let fixture: ComponentFixture<TaskRequestDetailComponent>;
    const route = ({ data: of({ taskRequest: new TaskRequest(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TaskRequestDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TaskRequestDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskRequestDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskRequest).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
