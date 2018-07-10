import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AMSAUser } from 'app/shared/model/amsa-user.model';
import { AMSAUserService } from './amsa-user.service';
import { AMSAUserComponent } from './amsa-user.component';
import { AMSAUserDetailComponent } from './amsa-user-detail.component';
import { AMSAUserUpdateComponent } from './amsa-user-update.component';
import { AMSAUserDeletePopupComponent } from './amsa-user-delete-dialog.component';
import { IAMSAUser } from 'app/shared/model/amsa-user.model';

@Injectable({ providedIn: 'root' })
export class AMSAUserResolve implements Resolve<IAMSAUser> {
  constructor(private service: AMSAUserService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((aMSAUser: HttpResponse<AMSAUser>) => aMSAUser.body));
    }
    return of(new AMSAUser());
  }
}

export const aMSAUserRoute: Routes = [
  {
    path: 'amsa-user',
    component: AMSAUserComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.aMSAUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'amsa-user/:id/view',
    component: AMSAUserDetailComponent,
    resolve: {
      aMSAUser: AMSAUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.aMSAUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'amsa-user/new',
    component: AMSAUserUpdateComponent,
    resolve: {
      aMSAUser: AMSAUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.aMSAUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'amsa-user/:id/edit',
    component: AMSAUserUpdateComponent,
    resolve: {
      aMSAUser: AMSAUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.aMSAUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const aMSAUserPopupRoute: Routes = [
  {
    path: 'amsa-user/:id/delete',
    component: AMSAUserDeletePopupComponent,
    resolve: {
      aMSAUser: AMSAUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.aMSAUser.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
