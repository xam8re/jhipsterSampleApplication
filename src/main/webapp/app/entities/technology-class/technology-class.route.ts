import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TechnologyClass } from 'app/shared/model/technology-class.model';
import { TechnologyClassService } from './technology-class.service';
import { TechnologyClassComponent } from './technology-class.component';
import { TechnologyClassDetailComponent } from './technology-class-detail.component';
import { TechnologyClassUpdateComponent } from './technology-class-update.component';
import { TechnologyClassDeletePopupComponent } from './technology-class-delete-dialog.component';
import { ITechnologyClass } from 'app/shared/model/technology-class.model';

@Injectable({ providedIn: 'root' })
export class TechnologyClassResolve implements Resolve<ITechnologyClass> {
  constructor(private service: TechnologyClassService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((technologyClass: HttpResponse<TechnologyClass>) => technologyClass.body));
    }
    return of(new TechnologyClass());
  }
}

export const technologyClassRoute: Routes = [
  {
    path: 'technology-class',
    component: TechnologyClassComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.technologyClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'technology-class/:id/view',
    component: TechnologyClassDetailComponent,
    resolve: {
      technologyClass: TechnologyClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.technologyClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'technology-class/new',
    component: TechnologyClassUpdateComponent,
    resolve: {
      technologyClass: TechnologyClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.technologyClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'technology-class/:id/edit',
    component: TechnologyClassUpdateComponent,
    resolve: {
      technologyClass: TechnologyClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.technologyClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const technologyClassPopupRoute: Routes = [
  {
    path: 'technology-class/:id/delete',
    component: TechnologyClassDeletePopupComponent,
    resolve: {
      technologyClass: TechnologyClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.technologyClass.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
