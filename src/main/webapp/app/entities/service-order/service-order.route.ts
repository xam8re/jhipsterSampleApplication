import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ServiceOrder } from 'app/shared/model/service-order.model';
import { ServiceOrderService } from './service-order.service';
import { ServiceOrderComponent } from './service-order.component';
import { ServiceOrderDetailComponent } from './service-order-detail.component';
import { ServiceOrderUpdateComponent } from './service-order-update.component';
import { ServiceOrderDeletePopupComponent } from './service-order-delete-dialog.component';
import { IServiceOrder } from 'app/shared/model/service-order.model';

@Injectable({ providedIn: 'root' })
export class ServiceOrderResolve implements Resolve<IServiceOrder> {
  constructor(private service: ServiceOrderService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((serviceOrder: HttpResponse<ServiceOrder>) => serviceOrder.body));
    }
    return of(new ServiceOrder());
  }
}

export const serviceOrderRoute: Routes = [
  {
    path: 'service-order',
    component: ServiceOrderComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.serviceOrder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'service-order/:id/view',
    component: ServiceOrderDetailComponent,
    resolve: {
      serviceOrder: ServiceOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceOrder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'service-order/new',
    component: ServiceOrderUpdateComponent,
    resolve: {
      serviceOrder: ServiceOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceOrder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'service-order/:id/edit',
    component: ServiceOrderUpdateComponent,
    resolve: {
      serviceOrder: ServiceOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceOrder.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceOrderPopupRoute: Routes = [
  {
    path: 'service-order/:id/delete',
    component: ServiceOrderDeletePopupComponent,
    resolve: {
      serviceOrder: ServiceOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceOrder.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
