/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TwitterKeyComponent } from '../../../../../../main/webapp/app/entities/twitter-key/twitter-key.component';
import { TwitterKeyService } from '../../../../../../main/webapp/app/entities/twitter-key/twitter-key.service';
import { TwitterKey } from '../../../../../../main/webapp/app/entities/twitter-key/twitter-key.model';

describe('Component Tests', () => {

    describe('TwitterKey Management Component', () => {
        let comp: TwitterKeyComponent;
        let fixture: ComponentFixture<TwitterKeyComponent>;
        let service: TwitterKeyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TwitterKeyComponent],
                providers: [
                    TwitterKeyService
                ]
            })
            .overrideTemplate(TwitterKeyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TwitterKeyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TwitterKeyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TwitterKey(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.twitterKeys[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
