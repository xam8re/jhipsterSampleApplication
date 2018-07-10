import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMachineModel } from 'app/shared/model/machine-model.model';
import { MachineModelService } from './machine-model.service';
import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerService } from 'app/entities/manufacturer';

@Component({
  selector: 'jhi-machine-model-update',
  templateUrl: './machine-model-update.component.html'
})
export class MachineModelUpdateComponent implements OnInit {
  private _machineModel: IMachineModel;
  isSaving: boolean;

  manufacturers: IManufacturer[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private machineModelService: MachineModelService,
    private manufacturerService: ManufacturerService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ machineModel }) => {
      this.machineModel = machineModel;
    });
    this.manufacturerService.query().subscribe(
      (res: HttpResponse<IManufacturer[]>) => {
        this.manufacturers = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.machineModel.id !== undefined) {
      this.subscribeToSaveResponse(this.machineModelService.update(this.machineModel));
    } else {
      this.subscribeToSaveResponse(this.machineModelService.create(this.machineModel));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IMachineModel>>) {
    result.subscribe((res: HttpResponse<IMachineModel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackManufacturerById(index: number, item: IManufacturer) {
    return item.id;
  }
  get machineModel() {
    return this._machineModel;
  }

  set machineModel(machineModel: IMachineModel) {
    this._machineModel = machineModel;
  }
}
