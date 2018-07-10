import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  TechnologyComponent,
  TechnologyDetailComponent,
  TechnologyUpdateComponent,
  TechnologyDeletePopupComponent,
  TechnologyDeleteDialogComponent,
  technologyRoute,
  technologyPopupRoute
} from './';

const ENTITY_STATES = [...technologyRoute, ...technologyPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TechnologyComponent,
    TechnologyDetailComponent,
    TechnologyUpdateComponent,
    TechnologyDeleteDialogComponent,
    TechnologyDeletePopupComponent
  ],
  entryComponents: [TechnologyComponent, TechnologyUpdateComponent, TechnologyDeleteDialogComponent, TechnologyDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTechnologyModule {}
