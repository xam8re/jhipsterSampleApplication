import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Manufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerService } from './manufacturer.service';
import { ManufacturerComponent } from './manufacturer.component';
import { ManufacturerDetailComponent } from './manufacturer-detail.component';
import { ManufacturerUpdateComponent } from './manufacturer-update.component';
import { ManufacturerDeletePopupComponent } from './manufacturer-delete-dialog.component';
import { IManufacturer } from 'app/shared/model/manufacturer.model';

@Injectable({ providedIn: 'root' })
export class ManufacturerResolve implements Resolve<IManufacturer> {
  constructor(private service: ManufacturerService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((manufacturer: HttpResponse<Manufacturer>) => manufacturer.body));
    }
    return of(new Manufacturer());
  }
}

export const manufacturerRoute: Routes = [
  {
    path: 'manufacturer',
    component: ManufacturerComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.manufacturer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'manufacturer/:id/view',
    component: ManufacturerDetailComponent,
    resolve: {
      manufacturer: ManufacturerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.manufacturer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'manufacturer/new',
    component: ManufacturerUpdateComponent,
    resolve: {
      manufacturer: ManufacturerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.manufacturer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'manufacturer/:id/edit',
    component: ManufacturerUpdateComponent,
    resolve: {
      manufacturer: ManufacturerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.manufacturer.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const manufacturerPopupRoute: Routes = [
  {
    path: 'manufacturer/:id/delete',
    component: ManufacturerDeletePopupComponent,
    resolve: {
      manufacturer: ManufacturerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.manufacturer.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
