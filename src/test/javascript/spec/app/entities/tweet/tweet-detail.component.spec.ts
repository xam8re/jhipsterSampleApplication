/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TweetDetailComponent } from '../../../../../../main/webapp/app/entities/tweet/tweet-detail.component';
import { TweetService } from '../../../../../../main/webapp/app/entities/tweet/tweet.service';
import { Tweet } from '../../../../../../main/webapp/app/entities/tweet/tweet.model';

describe('Component Tests', () => {

    describe('Tweet Management Detail Component', () => {
        let comp: TweetDetailComponent;
        let fixture: ComponentFixture<TweetDetailComponent>;
        let service: TweetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TweetDetailComponent],
                providers: [
                    TweetService
                ]
            })
            .overrideTemplate(TweetDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TweetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TweetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Tweet(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tweet).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
