/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TwitterKeyDetailComponent } from '../../../../../../main/webapp/app/entities/twitter-key/twitter-key-detail.component';
import { TwitterKeyService } from '../../../../../../main/webapp/app/entities/twitter-key/twitter-key.service';
import { TwitterKey } from '../../../../../../main/webapp/app/entities/twitter-key/twitter-key.model';

describe('Component Tests', () => {

    describe('TwitterKey Management Detail Component', () => {
        let comp: TwitterKeyDetailComponent;
        let fixture: ComponentFixture<TwitterKeyDetailComponent>;
        let service: TwitterKeyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TwitterKeyDetailComponent],
                providers: [
                    TwitterKeyService
                ]
            })
            .overrideTemplate(TwitterKeyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TwitterKeyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TwitterKeyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TwitterKey(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.twitterKey).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
