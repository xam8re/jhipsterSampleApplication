import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ServiceRequestClass } from 'app/shared/model/service-request-class.model';
import { ServiceRequestClassService } from './service-request-class.service';
import { ServiceRequestClassComponent } from './service-request-class.component';
import { ServiceRequestClassDetailComponent } from './service-request-class-detail.component';
import { ServiceRequestClassUpdateComponent } from './service-request-class-update.component';
import { ServiceRequestClassDeletePopupComponent } from './service-request-class-delete-dialog.component';
import { IServiceRequestClass } from 'app/shared/model/service-request-class.model';

@Injectable({ providedIn: 'root' })
export class ServiceRequestClassResolve implements Resolve<IServiceRequestClass> {
  constructor(private service: ServiceRequestClassService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((serviceRequestClass: HttpResponse<ServiceRequestClass>) => serviceRequestClass.body));
    }
    return of(new ServiceRequestClass());
  }
}

export const serviceRequestClassRoute: Routes = [
  {
    path: 'service-request-class',
    component: ServiceRequestClassComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.serviceRequestClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'service-request-class/:id/view',
    component: ServiceRequestClassDetailComponent,
    resolve: {
      serviceRequestClass: ServiceRequestClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceRequestClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'service-request-class/new',
    component: ServiceRequestClassUpdateComponent,
    resolve: {
      serviceRequestClass: ServiceRequestClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceRequestClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'service-request-class/:id/edit',
    component: ServiceRequestClassUpdateComponent,
    resolve: {
      serviceRequestClass: ServiceRequestClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceRequestClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceRequestClassPopupRoute: Routes = [
  {
    path: 'service-request-class/:id/delete',
    component: ServiceRequestClassDeletePopupComponent,
    resolve: {
      serviceRequestClass: ServiceRequestClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceRequestClass.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
