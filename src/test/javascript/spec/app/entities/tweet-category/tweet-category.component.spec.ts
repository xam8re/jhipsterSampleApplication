/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TweetCategoryComponent } from '../../../../../../main/webapp/app/entities/tweet-category/tweet-category.component';
import { TweetCategoryService } from '../../../../../../main/webapp/app/entities/tweet-category/tweet-category.service';
import { TweetCategory } from '../../../../../../main/webapp/app/entities/tweet-category/tweet-category.model';

describe('Component Tests', () => {

    describe('TweetCategory Management Component', () => {
        let comp: TweetCategoryComponent;
        let fixture: ComponentFixture<TweetCategoryComponent>;
        let service: TweetCategoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TweetCategoryComponent],
                providers: [
                    TweetCategoryService
                ]
            })
            .overrideTemplate(TweetCategoryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TweetCategoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TweetCategoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TweetCategory(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tweetCategories[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
