import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    TweetService,
    TweetPopupService,
    TweetComponent,
    TweetDetailComponent,
    TweetDialogComponent,
    TweetPopupComponent,
    TweetDeletePopupComponent,
    TweetDeleteDialogComponent,
    tweetRoute,
    tweetPopupRoute,
    TweetResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tweetRoute,
    ...tweetPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TweetComponent,
        TweetDetailComponent,
        TweetDialogComponent,
        TweetDeleteDialogComponent,
        TweetPopupComponent,
        TweetDeletePopupComponent,
    ],
    entryComponents: [
        TweetComponent,
        TweetDialogComponent,
        TweetPopupComponent,
        TweetDeleteDialogComponent,
        TweetDeletePopupComponent,
    ],
    providers: [
        TweetService,
        TweetPopupService,
        TweetResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTweetModule {}
