import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  TaskOfferComponent,
  TaskOfferDetailComponent,
  TaskOfferUpdateComponent,
  TaskOfferDeletePopupComponent,
  TaskOfferDeleteDialogComponent,
  taskOfferRoute,
  taskOfferPopupRoute
} from './';

const ENTITY_STATES = [...taskOfferRoute, ...taskOfferPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TaskOfferComponent,
    TaskOfferDetailComponent,
    TaskOfferUpdateComponent,
    TaskOfferDeleteDialogComponent,
    TaskOfferDeletePopupComponent
  ],
  entryComponents: [TaskOfferComponent, TaskOfferUpdateComponent, TaskOfferDeleteDialogComponent, TaskOfferDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTaskOfferModule {}
