/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TweetTemplateComponent } from '../../../../../../main/webapp/app/entities/tweet-template/tweet-template.component';
import { TweetTemplateService } from '../../../../../../main/webapp/app/entities/tweet-template/tweet-template.service';
import { TweetTemplate } from '../../../../../../main/webapp/app/entities/tweet-template/tweet-template.model';

describe('Component Tests', () => {

    describe('TweetTemplate Management Component', () => {
        let comp: TweetTemplateComponent;
        let fixture: ComponentFixture<TweetTemplateComponent>;
        let service: TweetTemplateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TweetTemplateComponent],
                providers: [
                    TweetTemplateService
                ]
            })
            .overrideTemplate(TweetTemplateComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TweetTemplateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TweetTemplateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TweetTemplate(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tweetTemplates[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
