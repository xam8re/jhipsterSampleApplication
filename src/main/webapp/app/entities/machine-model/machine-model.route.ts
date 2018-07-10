import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MachineModel } from 'app/shared/model/machine-model.model';
import { MachineModelService } from './machine-model.service';
import { MachineModelComponent } from './machine-model.component';
import { MachineModelDetailComponent } from './machine-model-detail.component';
import { MachineModelUpdateComponent } from './machine-model-update.component';
import { MachineModelDeletePopupComponent } from './machine-model-delete-dialog.component';
import { IMachineModel } from 'app/shared/model/machine-model.model';

@Injectable({ providedIn: 'root' })
export class MachineModelResolve implements Resolve<IMachineModel> {
  constructor(private service: MachineModelService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((machineModel: HttpResponse<MachineModel>) => machineModel.body));
    }
    return of(new MachineModel());
  }
}

export const machineModelRoute: Routes = [
  {
    path: 'machine-model',
    component: MachineModelComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.machineModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'machine-model/:id/view',
    component: MachineModelDetailComponent,
    resolve: {
      machineModel: MachineModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.machineModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'machine-model/new',
    component: MachineModelUpdateComponent,
    resolve: {
      machineModel: MachineModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.machineModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'machine-model/:id/edit',
    component: MachineModelUpdateComponent,
    resolve: {
      machineModel: MachineModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.machineModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const machineModelPopupRoute: Routes = [
  {
    path: 'machine-model/:id/delete',
    component: MachineModelDeletePopupComponent,
    resolve: {
      machineModel: MachineModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.machineModel.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
