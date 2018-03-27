/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TweetComponent } from '../../../../../../main/webapp/app/entities/tweet/tweet.component';
import { TweetService } from '../../../../../../main/webapp/app/entities/tweet/tweet.service';
import { Tweet } from '../../../../../../main/webapp/app/entities/tweet/tweet.model';

describe('Component Tests', () => {

    describe('Tweet Management Component', () => {
        let comp: TweetComponent;
        let fixture: ComponentFixture<TweetComponent>;
        let service: TweetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TweetComponent],
                providers: [
                    TweetService
                ]
            })
            .overrideTemplate(TweetComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TweetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TweetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Tweet(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tweets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
