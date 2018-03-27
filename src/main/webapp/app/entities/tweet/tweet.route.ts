import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TweetComponent } from './tweet.component';
import { TweetDetailComponent } from './tweet-detail.component';
import { TweetPopupComponent } from './tweet-dialog.component';
import { TweetDeletePopupComponent } from './tweet-delete-dialog.component';

@Injectable()
export class TweetResolvePagingParams implements Resolve<any> {

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

export const tweetRoute: Routes = [
    {
        path: 'tweet',
        component: TweetComponent,
        resolve: {
            'pagingParams': TweetResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tweet/:id',
        component: TweetDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tweetPopupRoute: Routes = [
    {
        path: 'tweet-new',
        component: TweetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tweet/:id/edit',
        component: TweetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tweet/:id/delete',
        component: TweetDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
