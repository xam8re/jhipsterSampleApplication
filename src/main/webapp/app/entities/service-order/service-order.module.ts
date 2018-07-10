import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ServiceOrderComponent,
  ServiceOrderDetailComponent,
  ServiceOrderUpdateComponent,
  ServiceOrderDeletePopupComponent,
  ServiceOrderDeleteDialogComponent,
  serviceOrderRoute,
  serviceOrderPopupRoute
} from './';

const ENTITY_STATES = [...serviceOrderRoute, ...serviceOrderPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceOrderComponent,
    ServiceOrderDetailComponent,
    ServiceOrderUpdateComponent,
    ServiceOrderDeleteDialogComponent,
    ServiceOrderDeletePopupComponent
  ],
  entryComponents: [
    ServiceOrderComponent,
    ServiceOrderUpdateComponent,
    ServiceOrderDeleteDialogComponent,
    ServiceOrderDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationServiceOrderModule {}
