import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TaskOrder } from 'app/shared/model/task-order.model';
import { TaskOrderService } from './task-order.service';
import { TaskOrderComponent } from './task-order.component';
import { TaskOrderDetailComponent } from './task-order-detail.component';
import { TaskOrderUpdateComponent } from './task-order-update.component';
import { TaskOrderDeletePopupComponent } from './task-order-delete-dialog.component';
import { ITaskOrder } from 'app/shared/model/task-order.model';

@Injectable({ providedIn: 'root' })
export class TaskOrderResolve implements Resolve<ITaskOrder> {
  constructor(private service: TaskOrderService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((taskOrder: HttpResponse<TaskOrder>) => taskOrder.body));
    }
    return of(new TaskOrder());
  }
}

export const taskOrderRoute: Routes = [
  {
    path: 'task-order',
    component: TaskOrderComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.taskOrder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'task-order/:id/view',
    component: TaskOrderDetailComponent,
    resolve: {
      taskOrder: TaskOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.taskOrder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'task-order/new',
    component: TaskOrderUpdateComponent,
    resolve: {
      taskOrder: TaskOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.taskOrder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'task-order/:id/edit',
    component: TaskOrderUpdateComponent,
    resolve: {
      taskOrder: TaskOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.taskOrder.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const taskOrderPopupRoute: Routes = [
  {
    path: 'task-order/:id/delete',
    component: TaskOrderDeletePopupComponent,
    resolve: {
      taskOrder: TaskOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.taskOrder.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
