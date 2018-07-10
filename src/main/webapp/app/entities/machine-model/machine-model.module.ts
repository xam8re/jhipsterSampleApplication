import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  MachineModelComponent,
  MachineModelDetailComponent,
  MachineModelUpdateComponent,
  MachineModelDeletePopupComponent,
  MachineModelDeleteDialogComponent,
  machineModelRoute,
  machineModelPopupRoute
} from './';

const ENTITY_STATES = [...machineModelRoute, ...machineModelPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MachineModelComponent,
    MachineModelDetailComponent,
    MachineModelUpdateComponent,
    MachineModelDeleteDialogComponent,
    MachineModelDeletePopupComponent
  ],
  entryComponents: [
    MachineModelComponent,
    MachineModelUpdateComponent,
    MachineModelDeleteDialogComponent,
    MachineModelDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationMachineModelModule {}
