/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { JobDataComponent } from '../../../../../../main/webapp/app/entities/job-data/job-data.component';
import { JobDataService } from '../../../../../../main/webapp/app/entities/job-data/job-data.service';
import { JobData } from '../../../../../../main/webapp/app/entities/job-data/job-data.model';

describe('Component Tests', () => {

    describe('JobData Management Component', () => {
        let comp: JobDataComponent;
        let fixture: ComponentFixture<JobDataComponent>;
        let service: JobDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [JobDataComponent],
                providers: [
                    JobDataService
                ]
            })
            .overrideTemplate(JobDataComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobDataComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobDataService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new JobData(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.jobData[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
