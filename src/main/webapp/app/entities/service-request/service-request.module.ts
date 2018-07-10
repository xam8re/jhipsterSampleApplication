import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ServiceRequestComponent,
  ServiceRequestDetailComponent,
  ServiceRequestUpdateComponent,
  ServiceRequestDeletePopupComponent,
  ServiceRequestDeleteDialogComponent,
  serviceRequestRoute,
  serviceRequestPopupRoute
} from './';

const ENTITY_STATES = [...serviceRequestRoute, ...serviceRequestPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceRequestComponent,
    ServiceRequestDetailComponent,
    ServiceRequestUpdateComponent,
    ServiceRequestDeleteDialogComponent,
    ServiceRequestDeletePopupComponent
  ],
  entryComponents: [
    ServiceRequestComponent,
    ServiceRequestUpdateComponent,
    ServiceRequestDeleteDialogComponent,
    ServiceRequestDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationServiceRequestModule {}
