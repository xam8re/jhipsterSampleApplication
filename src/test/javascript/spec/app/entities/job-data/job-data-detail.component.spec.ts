/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { JobDataDetailComponent } from '../../../../../../main/webapp/app/entities/job-data/job-data-detail.component';
import { JobDataService } from '../../../../../../main/webapp/app/entities/job-data/job-data.service';
import { JobData } from '../../../../../../main/webapp/app/entities/job-data/job-data.model';

describe('Component Tests', () => {

    describe('JobData Management Detail Component', () => {
        let comp: JobDataDetailComponent;
        let fixture: ComponentFixture<JobDataDetailComponent>;
        let service: JobDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [JobDataDetailComponent],
                providers: [
                    JobDataService
                ]
            })
            .overrideTemplate(JobDataDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobDataDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobDataService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new JobData(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.jobData).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
