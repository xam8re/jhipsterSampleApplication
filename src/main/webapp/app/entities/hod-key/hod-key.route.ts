import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { HodKeyComponent } from './hod-key.component';
import { HodKeyDetailComponent } from './hod-key-detail.component';
import { HodKeyPopupComponent } from './hod-key-dialog.component';
import { HodKeyDeletePopupComponent } from './hod-key-delete-dialog.component';

@Injectable()
export class HodKeyResolvePagingParams implements Resolve<any> {

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

export const hodKeyRoute: Routes = [
    {
        path: 'hod-key',
        component: HodKeyComponent,
        resolve: {
            'pagingParams': HodKeyResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.hodKey.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hod-key/:id',
        component: HodKeyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.hodKey.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hodKeyPopupRoute: Routes = [
    {
        path: 'hod-key-new',
        component: HodKeyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.hodKey.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hod-key/:id/edit',
        component: HodKeyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.hodKey.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hod-key/:id/delete',
        component: HodKeyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.hodKey.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
