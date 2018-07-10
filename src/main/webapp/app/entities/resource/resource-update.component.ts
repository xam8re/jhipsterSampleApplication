import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IResource } from 'app/shared/model/resource.model';
import { ResourceService } from './resource.service';
import { IMachineModel } from 'app/shared/model/machine-model.model';
import { MachineModelService } from 'app/entities/machine-model';
import { IDimension } from 'app/shared/model/dimension.model';
import { DimensionService } from 'app/entities/dimension';
import { IAMSAUser } from 'app/shared/model/amsa-user.model';
import { AMSAUserService } from 'app/entities/amsa-user';

@Component({
  selector: 'jhi-resource-update',
  templateUrl: './resource-update.component.html'
})
export class ResourceUpdateComponent implements OnInit {
  private _resource: IResource;
  isSaving: boolean;

  machinemodels: IMachineModel[];

  dimensions: IDimension[];

  amsausers: IAMSAUser[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private resourceService: ResourceService,
    private machineModelService: MachineModelService,
    private dimensionService: DimensionService,
    private aMSAUserService: AMSAUserService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ resource }) => {
      this.resource = resource;
    });
    this.machineModelService.query().subscribe(
      (res: HttpResponse<IMachineModel[]>) => {
        this.machinemodels = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.dimensionService.query().subscribe(
      (res: HttpResponse<IDimension[]>) => {
        this.dimensions = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.aMSAUserService.query().subscribe(
      (res: HttpResponse<IAMSAUser[]>) => {
        this.amsausers = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.resource.id !== undefined) {
      this.subscribeToSaveResponse(this.resourceService.update(this.resource));
    } else {
      this.subscribeToSaveResponse(this.resourceService.create(this.resource));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IResource>>) {
    result.subscribe((res: HttpResponse<IResource>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackMachineModelById(index: number, item: IMachineModel) {
    return item.id;
  }

  trackDimensionById(index: number, item: IDimension) {
    return item.id;
  }

  trackAMSAUserById(index: number, item: IAMSAUser) {
    return item.id;
  }
  get resource() {
    return this._resource;
  }

  set resource(resource: IResource) {
    this._resource = resource;
  }
}
