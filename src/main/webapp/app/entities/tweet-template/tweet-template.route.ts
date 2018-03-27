import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TweetTemplateComponent } from './tweet-template.component';
import { TweetTemplateDetailComponent } from './tweet-template-detail.component';
import { TweetTemplatePopupComponent } from './tweet-template-dialog.component';
import { TweetTemplateDeletePopupComponent } from './tweet-template-delete-dialog.component';

@Injectable()
export class TweetTemplateResolvePagingParams implements Resolve<any> {

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

export const tweetTemplateRoute: Routes = [
    {
        path: 'tweet-template',
        component: TweetTemplateComponent,
        resolve: {
            'pagingParams': TweetTemplateResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweetTemplate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tweet-template/:id',
        component: TweetTemplateDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweetTemplate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tweetTemplatePopupRoute: Routes = [
    {
        path: 'tweet-template-new',
        component: TweetTemplatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweetTemplate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tweet-template/:id/edit',
        component: TweetTemplatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweetTemplate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tweet-template/:id/delete',
        component: TweetTemplateDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.tweetTemplate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
