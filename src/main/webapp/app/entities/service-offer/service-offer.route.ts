import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ServiceOffer } from 'app/shared/model/service-offer.model';
import { ServiceOfferService } from './service-offer.service';
import { ServiceOfferComponent } from './service-offer.component';
import { ServiceOfferDetailComponent } from './service-offer-detail.component';
import { ServiceOfferUpdateComponent } from './service-offer-update.component';
import { ServiceOfferDeletePopupComponent } from './service-offer-delete-dialog.component';
import { IServiceOffer } from 'app/shared/model/service-offer.model';

@Injectable({ providedIn: 'root' })
export class ServiceOfferResolve implements Resolve<IServiceOffer> {
  constructor(private service: ServiceOfferService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((serviceOffer: HttpResponse<ServiceOffer>) => serviceOffer.body));
    }
    return of(new ServiceOffer());
  }
}

export const serviceOfferRoute: Routes = [
  {
    path: 'service-offer',
    component: ServiceOfferComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.serviceOffer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'service-offer/:id/view',
    component: ServiceOfferDetailComponent,
    resolve: {
      serviceOffer: ServiceOfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceOffer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'service-offer/new',
    component: ServiceOfferUpdateComponent,
    resolve: {
      serviceOffer: ServiceOfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceOffer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'service-offer/:id/edit',
    component: ServiceOfferUpdateComponent,
    resolve: {
      serviceOffer: ServiceOfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceOffer.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceOfferPopupRoute: Routes = [
  {
    path: 'service-offer/:id/delete',
    component: ServiceOfferDeletePopupComponent,
    resolve: {
      serviceOffer: ServiceOfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.serviceOffer.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
