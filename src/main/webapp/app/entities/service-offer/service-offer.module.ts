import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ServiceOfferComponent,
  ServiceOfferDetailComponent,
  ServiceOfferUpdateComponent,
  ServiceOfferDeletePopupComponent,
  ServiceOfferDeleteDialogComponent,
  serviceOfferRoute,
  serviceOfferPopupRoute
} from './';

const ENTITY_STATES = [...serviceOfferRoute, ...serviceOfferPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceOfferComponent,
    ServiceOfferDetailComponent,
    ServiceOfferUpdateComponent,
    ServiceOfferDeleteDialogComponent,
    ServiceOfferDeletePopupComponent
  ],
  entryComponents: [
    ServiceOfferComponent,
    ServiceOfferUpdateComponent,
    ServiceOfferDeleteDialogComponent,
    ServiceOfferDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationServiceOfferModule {}
