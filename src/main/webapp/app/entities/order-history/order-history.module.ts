import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  OrderHistoryComponent,
  OrderHistoryDetailComponent,
  OrderHistoryUpdateComponent,
  OrderHistoryDeletePopupComponent,
  OrderHistoryDeleteDialogComponent,
  orderHistoryRoute,
  orderHistoryPopupRoute
} from './';

const ENTITY_STATES = [...orderHistoryRoute, ...orderHistoryPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OrderHistoryComponent,
    OrderHistoryDetailComponent,
    OrderHistoryUpdateComponent,
    OrderHistoryDeleteDialogComponent,
    OrderHistoryDeletePopupComponent
  ],
  entryComponents: [
    OrderHistoryComponent,
    OrderHistoryUpdateComponent,
    OrderHistoryDeleteDialogComponent,
    OrderHistoryDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationOrderHistoryModule {}
