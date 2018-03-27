import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TwitterKeyComponent } from './twitter-key.component';
import { TwitterKeyDetailComponent } from './twitter-key-detail.component';
import { TwitterKeyPopupComponent } from './twitter-key-dialog.component';
import { TwitterKeyDeletePopupComponent } from './twitter-key-delete-dialog.component';

@Injectable()
export class TwitterKeyResolvePagingParams implements Resolve<any> {

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

export const twitterKeyRoute: Routes = [
    {
        path: 'twitter-key',
        component: TwitterKeyComponent,
        resolve: {
            'pagingParams': TwitterKeyResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.twitterKey.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'twitter-key/:id',
        component: TwitterKeyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.twitterKey.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const twitterKeyPopupRoute: Routes = [
    {
        path: 'twitter-key-new',
        component: TwitterKeyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.twitterKey.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'twitter-key/:id/edit',
        component: TwitterKeyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.twitterKey.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'twitter-key/:id/delete',
        component: TwitterKeyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.twitterKey.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
