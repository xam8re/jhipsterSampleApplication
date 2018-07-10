import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  MaterialComponent,
  MaterialDetailComponent,
  MaterialUpdateComponent,
  MaterialDeletePopupComponent,
  MaterialDeleteDialogComponent,
  materialRoute,
  materialPopupRoute
} from './';

const ENTITY_STATES = [...materialRoute, ...materialPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MaterialComponent,
    MaterialDetailComponent,
    MaterialUpdateComponent,
    MaterialDeleteDialogComponent,
    MaterialDeletePopupComponent
  ],
  entryComponents: [MaterialComponent, MaterialUpdateComponent, MaterialDeleteDialogComponent, MaterialDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationMaterialModule {}
