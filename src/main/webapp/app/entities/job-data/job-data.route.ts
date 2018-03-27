import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { JobDataComponent } from './job-data.component';
import { JobDataDetailComponent } from './job-data-detail.component';
import { JobDataPopupComponent } from './job-data-dialog.component';
import { JobDataDeletePopupComponent } from './job-data-delete-dialog.component';

@Injectable()
export class JobDataResolvePagingParams implements Resolve<any> {

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

export const jobDataRoute: Routes = [
    {
        path: 'job-data',
        component: JobDataComponent,
        resolve: {
            'pagingParams': JobDataResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.jobData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'job-data/:id',
        component: JobDataDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.jobData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobDataPopupRoute: Routes = [
    {
        path: 'job-data-new',
        component: JobDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.jobData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-data/:id/edit',
        component: JobDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.jobData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-data/:id/delete',
        component: JobDataDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.jobData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
