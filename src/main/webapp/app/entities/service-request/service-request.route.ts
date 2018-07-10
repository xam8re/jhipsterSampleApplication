import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ServiceRequest } from 'app/shared/model/service-request.model';
import { ServiceRequestService } from './service-request.service';
import { ServiceRequestComponent } from './service-request.component';
import { ServiceRequestDetailComponent } from './service-request-detail.component';
import { ServiceRequestUpdateComponent } from './service-request-update.component';
import { ServiceRequestDeletePopupComponent } from './service-request-delete-dialog.component';
import { IServiceRequest } from 'app/shared/model/service-request.model';

@Injectable({ providedIn: 'root' })
export class ServiceRequestResolve implements Resolve<IServiceRequest> {
  constructor(private service: ServiceRequestService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((serviceRequest: HttpResponse<ServiceRequest>) => serviceRequest.body));
    }
    return of(new ServiceRequest());
  }
}

export const serviceRequestRoute: Routes = [
  {
    path: 'service-request',
    component: ServiceRequestComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.serviceRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'service-request/:id/view',
    component: ServiceRequestDetailComponent,
    resolve: {
      serviceRequest: ServiceRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'service-request/new',
    component: ServiceRequestUpdateComponent,
    resolve: {
      serviceRequest: ServiceRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'service-request/:id/edit',
    component: ServiceRequestUpdateComponent,
    resolve: {
      serviceRequest: ServiceRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceRequestPopupRoute: Routes = [
  {
    path: 'service-request/:id/delete',
    component: ServiceRequestDeletePopupComponent,
    resolve: {
      serviceRequest: ServiceRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceRequest.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
