import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ServiceRequestClassComponent,
  ServiceRequestClassDetailComponent,
  ServiceRequestClassUpdateComponent,
  ServiceRequestClassDeletePopupComponent,
  ServiceRequestClassDeleteDialogComponent,
  serviceRequestClassRoute,
  serviceRequestClassPopupRoute
} from './';

const ENTITY_STATES = [...serviceRequestClassRoute, ...serviceRequestClassPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceRequestClassComponent,
    ServiceRequestClassDetailComponent,
    ServiceRequestClassUpdateComponent,
    ServiceRequestClassDeleteDialogComponent,
    ServiceRequestClassDeletePopupComponent
  ],
  entryComponents: [
    ServiceRequestClassComponent,
    ServiceRequestClassUpdateComponent,
    ServiceRequestClassDeleteDialogComponent,
    ServiceRequestClassDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationServiceRequestClassModule {}
