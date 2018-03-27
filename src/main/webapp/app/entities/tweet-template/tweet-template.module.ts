import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    TweetTemplateService,
    TweetTemplatePopupService,
    TweetTemplateComponent,
    TweetTemplateDetailComponent,
    TweetTemplateDialogComponent,
    TweetTemplatePopupComponent,
    TweetTemplateDeletePopupComponent,
    TweetTemplateDeleteDialogComponent,
    tweetTemplateRoute,
    tweetTemplatePopupRoute,
    TweetTemplateResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tweetTemplateRoute,
    ...tweetTemplatePopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TweetTemplateComponent,
        TweetTemplateDetailComponent,
        TweetTemplateDialogComponent,
        TweetTemplateDeleteDialogComponent,
        TweetTemplatePopupComponent,
        TweetTemplateDeletePopupComponent,
    ],
    entryComponents: [
        TweetTemplateComponent,
        TweetTemplateDialogComponent,
        TweetTemplatePopupComponent,
        TweetTemplateDeleteDialogComponent,
        TweetTemplateDeletePopupComponent,
    ],
    providers: [
        TweetTemplateService,
        TweetTemplatePopupService,
        TweetTemplateResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTweetTemplateModule {}
