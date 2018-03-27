/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { HodKeyDetailComponent } from '../../../../../../main/webapp/app/entities/hod-key/hod-key-detail.component';
import { HodKeyService } from '../../../../../../main/webapp/app/entities/hod-key/hod-key.service';
import { HodKey } from '../../../../../../main/webapp/app/entities/hod-key/hod-key.model';

describe('Component Tests', () => {

    describe('HodKey Management Detail Component', () => {
        let comp: HodKeyDetailComponent;
        let fixture: ComponentFixture<HodKeyDetailComponent>;
        let service: HodKeyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [HodKeyDetailComponent],
                providers: [
                    HodKeyService
                ]
            })
            .overrideTemplate(HodKeyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HodKeyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HodKeyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new HodKey(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.hodKey).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
