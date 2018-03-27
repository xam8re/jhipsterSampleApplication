import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    JobDataService,
    JobDataPopupService,
    JobDataComponent,
    JobDataDetailComponent,
    JobDataDialogComponent,
    JobDataPopupComponent,
    JobDataDeletePopupComponent,
    JobDataDeleteDialogComponent,
    jobDataRoute,
    jobDataPopupRoute,
    JobDataResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...jobDataRoute,
    ...jobDataPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        JobDataComponent,
        JobDataDetailComponent,
        JobDataDialogComponent,
        JobDataDeleteDialogComponent,
        JobDataPopupComponent,
        JobDataDeletePopupComponent,
    ],
    entryComponents: [
        JobDataComponent,
        JobDataDialogComponent,
        JobDataPopupComponent,
        JobDataDeleteDialogComponent,
        JobDataDeletePopupComponent,
    ],
    providers: [
        JobDataService,
        JobDataPopupService,
        JobDataResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationJobDataModule {}
