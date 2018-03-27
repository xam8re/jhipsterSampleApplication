/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { HodKeyComponent } from '../../../../../../main/webapp/app/entities/hod-key/hod-key.component';
import { HodKeyService } from '../../../../../../main/webapp/app/entities/hod-key/hod-key.service';
import { HodKey } from '../../../../../../main/webapp/app/entities/hod-key/hod-key.model';

describe('Component Tests', () => {

    describe('HodKey Management Component', () => {
        let comp: HodKeyComponent;
        let fixture: ComponentFixture<HodKeyComponent>;
        let service: HodKeyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [HodKeyComponent],
                providers: [
                    HodKeyService
                ]
            })
            .overrideTemplate(HodKeyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HodKeyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HodKeyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new HodKey(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.hodKeys[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
