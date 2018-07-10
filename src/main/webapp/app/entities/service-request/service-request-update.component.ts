import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IServiceRequest } from 'app/shared/model/service-request.model';
import { ServiceRequestService } from './service-request.service';
import { IMaterial } from 'app/shared/model/material.model';
import { MaterialService } from 'app/entities/material';
import { IDimension } from 'app/shared/model/dimension.model';
import { DimensionService } from 'app/entities/dimension';
import { IServiceRequestClass } from 'app/shared/model/service-request-class.model';
import { ServiceRequestClassService } from 'app/entities/service-request-class';
import { IAMSAUser } from 'app/shared/model/amsa-user.model';
import { AMSAUserService } from 'app/entities/amsa-user';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document';

@Component({
  selector: 'jhi-service-request-update',
  templateUrl: './service-request-update.component.html'
})
export class ServiceRequestUpdateComponent implements OnInit {
  private _serviceRequest: IServiceRequest;
  isSaving: boolean;

  materials: IMaterial[];

  dimensions: IDimension[];

  servicerequestclasses: IServiceRequestClass[];

  amsausers: IAMSAUser[];

  documents: IDocument[];
  period: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private serviceRequestService: ServiceRequestService,
    private materialService: MaterialService,
    private dimensionService: DimensionService,
    private serviceRequestClassService: ServiceRequestClassService,
    private aMSAUserService: AMSAUserService,
    private documentService: DocumentService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceRequest }) => {
      this.serviceRequest = serviceRequest;
    });
    this.materialService.query().subscribe(
      (res: HttpResponse<IMaterial[]>) => {
        this.materials = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.dimensionService.query().subscribe(
      (res: HttpResponse<IDimension[]>) => {
        this.dimensions = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.serviceRequestClassService.query().subscribe(
      (res: HttpResponse<IServiceRequestClass[]>) => {
        this.servicerequestclasses = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.aMSAUserService.query().subscribe(
      (res: HttpResponse<IAMSAUser[]>) => {
        this.amsausers = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.documentService.query().subscribe(
      (res: HttpResponse<IDocument[]>) => {
        this.documents = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.serviceRequest.period = moment(this.period, DATE_TIME_FORMAT);
    if (this.serviceRequest.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceRequestService.update(this.serviceRequest));
    } else {
      this.subscribeToSaveResponse(this.serviceRequestService.create(this.serviceRequest));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IServiceRequest>>) {
    result.subscribe((res: HttpResponse<IServiceRequest>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackMaterialById(index: number, item: IMaterial) {
    return item.id;
  }

  trackDimensionById(index: number, item: IDimension) {
    return item.id;
  }

  trackServiceRequestClassById(index: number, item: IServiceRequestClass) {
    return item.id;
  }

  trackAMSAUserById(index: number, item: IAMSAUser) {
    return item.id;
  }

  trackDocumentById(index: number, item: IDocument) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
  get serviceRequest() {
    return this._serviceRequest;
  }

  set serviceRequest(serviceRequest: IServiceRequest) {
    this._serviceRequest = serviceRequest;
    this.period = moment(serviceRequest.period).format(DATE_TIME_FORMAT);
  }
}
