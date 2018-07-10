import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TaskRequest } from 'app/shared/model/task-request.model';
import { TaskRequestService } from './task-request.service';
import { TaskRequestComponent } from './task-request.component';
import { TaskRequestDetailComponent } from './task-request-detail.component';
import { TaskRequestUpdateComponent } from './task-request-update.component';
import { TaskRequestDeletePopupComponent } from './task-request-delete-dialog.component';
import { ITaskRequest } from 'app/shared/model/task-request.model';

@Injectable({ providedIn: 'root' })
export class TaskRequestResolve implements Resolve<ITaskRequest> {
  constructor(private service: TaskRequestService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((taskRequest: HttpResponse<TaskRequest>) => taskRequest.body));
    }
    return of(new TaskRequest());
  }
}

export const taskRequestRoute: Routes = [
  {
    path: 'task-request',
    component: TaskRequestComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.taskRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'task-request/:id/view',
    component: TaskRequestDetailComponent,
    resolve: {
      taskRequest: TaskRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.taskRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'task-request/new',
    component: TaskRequestUpdateComponent,
    resolve: {
      taskRequest: TaskRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.taskRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'task-request/:id/edit',
    component: TaskRequestUpdateComponent,
    resolve: {
      taskRequest: TaskRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.taskRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const taskRequestPopupRoute: Routes = [
  {
    path: 'task-request/:id/delete',
    component: TaskRequestDeletePopupComponent,
    resolve: {
      taskRequest: TaskRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.taskRequest.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
