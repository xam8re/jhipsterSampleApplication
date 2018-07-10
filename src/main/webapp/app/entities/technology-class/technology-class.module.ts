import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  TechnologyClassComponent,
  TechnologyClassDetailComponent,
  TechnologyClassUpdateComponent,
  TechnologyClassDeletePopupComponent,
  TechnologyClassDeleteDialogComponent,
  technologyClassRoute,
  technologyClassPopupRoute
} from './';

const ENTITY_STATES = [...technologyClassRoute, ...technologyClassPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TechnologyClassComponent,
    TechnologyClassDetailComponent,
    TechnologyClassUpdateComponent,
    TechnologyClassDeleteDialogComponent,
    TechnologyClassDeletePopupComponent
  ],
  entryComponents: [
    TechnologyClassComponent,
    TechnologyClassUpdateComponent,
    TechnologyClassDeleteDialogComponent,
    TechnologyClassDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTechnologyClassModule {}
