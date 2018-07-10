import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Technology } from 'app/shared/model/technology.model';
import { TechnologyService } from './technology.service';
import { TechnologyComponent } from './technology.component';
import { TechnologyDetailComponent } from './technology-detail.component';
import { TechnologyUpdateComponent } from './technology-update.component';
import { TechnologyDeletePopupComponent } from './technology-delete-dialog.component';
import { ITechnology } from 'app/shared/model/technology.model';

@Injectable({ providedIn: 'root' })
export class TechnologyResolve implements Resolve<ITechnology> {
  constructor(private service: TechnologyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((technology: HttpResponse<Technology>) => technology.body));
    }
    return of(new Technology());
  }
}

export const technologyRoute: Routes = [
  {
    path: 'technology',
    component: TechnologyComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.technology.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'technology/:id/view',
    component: TechnologyDetailComponent,
    resolve: {
      technology: TechnologyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.technology.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'technology/new',
    component: TechnologyUpdateComponent,
    resolve: {
      technology: TechnologyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.technology.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'technology/:id/edit',
    component: TechnologyUpdateComponent,
    resolve: {
      technology: TechnologyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.technology.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const technologyPopupRoute: Routes = [
  {
    path: 'technology/:id/delete',
    component: TechnologyDeletePopupComponent,
    resolve: {
      technology: TechnologyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.technology.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
