import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TaskOffer } from 'app/shared/model/task-offer.model';
import { TaskOfferService } from './task-offer.service';
import { TaskOfferComponent } from './task-offer.component';
import { TaskOfferDetailComponent } from './task-offer-detail.component';
import { TaskOfferUpdateComponent } from './task-offer-update.component';
import { TaskOfferDeletePopupComponent } from './task-offer-delete-dialog.component';
import { ITaskOffer } from 'app/shared/model/task-offer.model';

@Injectable({ providedIn: 'root' })
export class TaskOfferResolve implements Resolve<ITaskOffer> {
  constructor(private service: TaskOfferService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((taskOffer: HttpResponse<TaskOffer>) => taskOffer.body));
    }
    return of(new TaskOffer());
  }
}

export const taskOfferRoute: Routes = [
  {
    path: 'task-offer',
    component: TaskOfferComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.taskOffer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'task-offer/:id/view',
    component: TaskOfferDetailComponent,
    resolve: {
      taskOffer: TaskOfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.taskOffer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'task-offer/new',
    component: TaskOfferUpdateComponent,
    resolve: {
      taskOffer: TaskOfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.taskOffer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'task-offer/:id/edit',
    component: TaskOfferUpdateComponent,
    resolve: {
      taskOffer: TaskOfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.taskOffer.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const taskOfferPopupRoute: Routes = [
  {
    path: 'task-offer/:id/delete',
    component: TaskOfferDeletePopupComponent,
    resolve: {
      taskOffer: TaskOfferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.taskOffer.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
