import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    HodKeyService,
    HodKeyPopupService,
    HodKeyComponent,
    HodKeyDetailComponent,
    HodKeyDialogComponent,
    HodKeyPopupComponent,
    HodKeyDeletePopupComponent,
    HodKeyDeleteDialogComponent,
    hodKeyRoute,
    hodKeyPopupRoute,
    HodKeyResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...hodKeyRoute,
    ...hodKeyPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HodKeyComponent,
        HodKeyDetailComponent,
        HodKeyDialogComponent,
        HodKeyDeleteDialogComponent,
        HodKeyPopupComponent,
        HodKeyDeletePopupComponent,
    ],
    entryComponents: [
        HodKeyComponent,
        HodKeyDialogComponent,
        HodKeyPopupComponent,
        HodKeyDeleteDialogComponent,
        HodKeyDeletePopupComponent,
    ],
    providers: [
        HodKeyService,
        HodKeyPopupService,
        HodKeyResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationHodKeyModule {}
