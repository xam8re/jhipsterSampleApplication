import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  TaskRequestComponent,
  TaskRequestDetailComponent,
  TaskRequestUpdateComponent,
  TaskRequestDeletePopupComponent,
  TaskRequestDeleteDialogComponent,
  taskRequestRoute,
  taskRequestPopupRoute
} from './';

const ENTITY_STATES = [...taskRequestRoute, ...taskRequestPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TaskRequestComponent,
    TaskRequestDetailComponent,
    TaskRequestUpdateComponent,
    TaskRequestDeleteDialogComponent,
    TaskRequestDeletePopupComponent
  ],
  entryComponents: [TaskRequestComponent, TaskRequestUpdateComponent, TaskRequestDeleteDialogComponent, TaskRequestDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTaskRequestModule {}
