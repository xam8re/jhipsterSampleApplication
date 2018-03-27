import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    TweetCategoryService,
    TweetCategoryPopupService,
    TweetCategoryComponent,
    TweetCategoryDetailComponent,
    TweetCategoryDialogComponent,
    TweetCategoryPopupComponent,
    TweetCategoryDeletePopupComponent,
    TweetCategoryDeleteDialogComponent,
    tweetCategoryRoute,
    tweetCategoryPopupRoute,
    TweetCategoryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tweetCategoryRoute,
    ...tweetCategoryPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TweetCategoryComponent,
        TweetCategoryDetailComponent,
        TweetCategoryDialogComponent,
        TweetCategoryDeleteDialogComponent,
        TweetCategoryPopupComponent,
        TweetCategoryDeletePopupComponent,
    ],
    entryComponents: [
        TweetCategoryComponent,
        TweetCategoryDialogComponent,
        TweetCategoryPopupComponent,
        TweetCategoryDeleteDialogComponent,
        TweetCategoryDeletePopupComponent,
    ],
    providers: [
        TweetCategoryService,
        TweetCategoryPopupService,
        TweetCategoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTweetCategoryModule {}
