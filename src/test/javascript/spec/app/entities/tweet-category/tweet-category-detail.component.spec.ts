/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TweetCategoryDetailComponent } from '../../../../../../main/webapp/app/entities/tweet-category/tweet-category-detail.component';
import { TweetCategoryService } from '../../../../../../main/webapp/app/entities/tweet-category/tweet-category.service';
import { TweetCategory } from '../../../../../../main/webapp/app/entities/tweet-category/tweet-category.model';

describe('Component Tests', () => {

    describe('TweetCategory Management Detail Component', () => {
        let comp: TweetCategoryDetailComponent;
        let fixture: ComponentFixture<TweetCategoryDetailComponent>;
        let service: TweetCategoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TweetCategoryDetailComponent],
                providers: [
                    TweetCategoryService
                ]
            })
            .overrideTemplate(TweetCategoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TweetCategoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TweetCategoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TweetCategory(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tweetCategory).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
