import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import { JhipsterSampleApplicationAdminModule } from 'app/admin/admin.module';
import {
  AMSAUserComponent,
  AMSAUserDetailComponent,
  AMSAUserUpdateComponent,
  AMSAUserDeletePopupComponent,
  AMSAUserDeleteDialogComponent,
  aMSAUserRoute,
  aMSAUserPopupRoute
} from './';

const ENTITY_STATES = [...aMSAUserRoute, ...aMSAUserPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, JhipsterSampleApplicationAdminModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AMSAUserComponent,
    AMSAUserDetailComponent,
    AMSAUserUpdateComponent,
    AMSAUserDeleteDialogComponent,
    AMSAUserDeletePopupComponent
  ],
  entryComponents: [AMSAUserComponent, AMSAUserUpdateComponent, AMSAUserDeleteDialogComponent, AMSAUserDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationAMSAUserModule {}
