import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ITaskRequest } from 'app/shared/model/task-request.model';
import { TaskRequestService } from './task-request.service';
import { IServiceRequest } from 'app/shared/model/service-request.model';
import { ServiceRequestService } from 'app/entities/service-request';
import { ITechnology } from 'app/shared/model/technology.model';
import { TechnologyService } from 'app/entities/technology';
import { IMaterial } from 'app/shared/model/material.model';
import { MaterialService } from 'app/entities/material';
import { IDimension } from 'app/shared/model/dimension.model';
import { DimensionService } from 'app/entities/dimension';

@Component({
  selector: 'jhi-task-request-update',
  templateUrl: './task-request-update.component.html'
})
export class TaskRequestUpdateComponent implements OnInit {
  private _taskRequest: ITaskRequest;
  isSaving: boolean;

  servicerequests: IServiceRequest[];

  technologies: ITechnology[];

  materials: IMaterial[];

  volumes: IDimension[];
  period: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private taskRequestService: TaskRequestService,
    private serviceRequestService: ServiceRequestService,
    private technologyService: TechnologyService,
    private materialService: MaterialService,
    private dimensionService: DimensionService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ taskRequest }) => {
      this.taskRequest = taskRequest;
    });
    this.serviceRequestService.query().subscribe(
      (res: HttpResponse<IServiceRequest[]>) => {
        this.servicerequests = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.technologyService.query({ filter: 'taskrequest-is-null' }).subscribe(
      (res: HttpResponse<ITechnology[]>) => {
        if (!this.taskRequest.technologyId) {
          this.technologies = res.body;
        } else {
          this.technologyService.find(this.taskRequest.technologyId).subscribe(
            (subRes: HttpResponse<ITechnology>) => {
              this.technologies = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.materialService.query({ filter: 'taskrequest-is-null' }).subscribe(
      (res: HttpResponse<IMaterial[]>) => {
        if (!this.taskRequest.materialId) {
          this.materials = res.body;
        } else {
          this.materialService.find(this.taskRequest.materialId).subscribe(
            (subRes: HttpResponse<IMaterial>) => {
              this.materials = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.dimensionService.query({ filter: 'taskrequest-is-null' }).subscribe(
      (res: HttpResponse<IDimension[]>) => {
        if (!this.taskRequest.volumeId) {
          this.volumes = res.body;
        } else {
          this.dimensionService.find(this.taskRequest.volumeId).subscribe(
            (subRes: HttpResponse<IDimension>) => {
              this.volumes = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.taskRequest.period = moment(this.period, DATE_TIME_FORMAT);
    if (this.taskRequest.id !== undefined) {
      this.subscribeToSaveResponse(this.taskRequestService.update(this.taskRequest));
    } else {
      this.subscribeToSaveResponse(this.taskRequestService.create(this.taskRequest));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ITaskRequest>>) {
    result.subscribe((res: HttpResponse<ITaskRequest>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackServiceRequestById(index: number, item: IServiceRequest) {
    return item.id;
  }

  trackTechnologyById(index: number, item: ITechnology) {
    return item.id;
  }

  trackMaterialById(index: number, item: IMaterial) {
    return item.id;
  }

  trackDimensionById(index: number, item: IDimension) {
    return item.id;
  }
  get taskRequest() {
    return this._taskRequest;
  }

  set taskRequest(taskRequest: ITaskRequest) {
    this._taskRequest = taskRequest;
    this.period = moment(taskRequest.period).format(DATE_TIME_FORMAT);
  }
}
