/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TweetTemplateDetailComponent } from '../../../../../../main/webapp/app/entities/tweet-template/tweet-template-detail.component';
import { TweetTemplateService } from '../../../../../../main/webapp/app/entities/tweet-template/tweet-template.service';
import { TweetTemplate } from '../../../../../../main/webapp/app/entities/tweet-template/tweet-template.model';

describe('Component Tests', () => {

    describe('TweetTemplate Management Detail Component', () => {
        let comp: TweetTemplateDetailComponent;
        let fixture: ComponentFixture<TweetTemplateDetailComponent>;
        let service: TweetTemplateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TweetTemplateDetailComponent],
                providers: [
                    TweetTemplateService
                ]
            })
            .overrideTemplate(TweetTemplateDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TweetTemplateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TweetTemplateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TweetTemplate(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tweetTemplate).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
