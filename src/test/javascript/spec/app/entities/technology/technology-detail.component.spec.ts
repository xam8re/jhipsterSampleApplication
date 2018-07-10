/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TechnologyDetailComponent } from 'app/entities/technology/technology-detail.component';
import { Technology } from 'app/shared/model/technology.model';

describe('Component Tests', () => {
  describe('Technology Management Detail Component', () => {
    let comp: TechnologyDetailComponent;
    let fixture: ComponentFixture<TechnologyDetailComponent>;
    const route = ({ data: of({ technology: new Technology(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TechnologyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TechnologyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TechnologyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.technology).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
