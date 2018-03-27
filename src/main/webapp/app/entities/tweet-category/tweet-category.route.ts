import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TweetCategoryComponent } from './tweet-category.component';
import { TweetCategoryDetailComponent } from './tweet-category-detail.component';
import { TweetCategoryPopupComponent } from './tweet-category-dialog.component';
import { TweetCategoryDeletePopupComponent } from './tweet-category-delete-dialog.component';

@Injectable()
export class TweetCategoryResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const tweetCategoryRoute: Routes = [
    {
        path: 'tweet-category',
        component: TweetCategoryComponent,
        resolve: {
            'pagingParams': TweetCategoryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweetCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tweet-category/:id',
        component: TweetCategoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweetCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tweetCategoryPopupRoute: Routes = [
    {
        path: 'tweet-category-new',
        component: TweetCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweetCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tweet-category/:id/edit',
        component: TweetCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweetCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tweet-category/:id/delete',
        component: TweetCategoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweetCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
