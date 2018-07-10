import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  TaskOrderComponent,
  TaskOrderDetailComponent,
  TaskOrderUpdateComponent,
  TaskOrderDeletePopupComponent,
  TaskOrderDeleteDialogComponent,
  taskOrderRoute,
  taskOrderPopupRoute
} from './';

const ENTITY_STATES = [...taskOrderRoute, ...taskOrderPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TaskOrderComponent,
    TaskOrderDetailComponent,
    TaskOrderUpdateComponent,
    TaskOrderDeleteDialogComponent,
    TaskOrderDeletePopupComponent
  ],
  entryComponents: [TaskOrderComponent, TaskOrderUpdateComponent, TaskOrderDeleteDialogComponent, TaskOrderDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTaskOrderModule {}
