import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    TwitterKeyService,
    TwitterKeyPopupService,
    TwitterKeyComponent,
    TwitterKeyDetailComponent,
    TwitterKeyDialogComponent,
    TwitterKeyPopupComponent,
    TwitterKeyDeletePopupComponent,
    TwitterKeyDeleteDialogComponent,
    twitterKeyRoute,
    twitterKeyPopupRoute,
    TwitterKeyResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...twitterKeyRoute,
    ...twitterKeyPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TwitterKeyComponent,
        TwitterKeyDetailComponent,
        TwitterKeyDialogComponent,
        TwitterKeyDeleteDialogComponent,
        TwitterKeyPopupComponent,
        TwitterKeyDeletePopupComponent,
    ],
    entryComponents: [
        TwitterKeyComponent,
        TwitterKeyDialogComponent,
        TwitterKeyPopupComponent,
        TwitterKeyDeleteDialogComponent,
        TwitterKeyDeletePopupComponent,
    ],
    providers: [
        TwitterKeyService,
        TwitterKeyPopupService,
        TwitterKeyResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTwitterKeyModule {}
