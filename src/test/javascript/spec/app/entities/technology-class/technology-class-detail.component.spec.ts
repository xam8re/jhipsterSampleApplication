/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TechnologyClassDetailComponent } from 'app/entities/technology-class/technology-class-detail.component';
import { TechnologyClass } from 'app/shared/model/technology-class.model';

describe('Component Tests', () => {
  describe('TechnologyClass Management Detail Component', () => {
    let comp: TechnologyClassDetailComponent;
    let fixture: ComponentFixture<TechnologyClassDetailComponent>;
    const route = ({ data: of({ technologyClass: new TechnologyClass(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TechnologyClassDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TechnologyClassDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TechnologyClassDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.technologyClass).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
