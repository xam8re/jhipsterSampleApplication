import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IServiceRequestClass } from 'app/shared/model/service-request-class.model';
import { ServiceRequestClassService } from './service-request-class.service';

@Component({
  selector: 'jhi-service-request-class-update',
  templateUrl: './service-request-class-update.component.html'
})
export class ServiceRequestClassUpdateComponent implements OnInit {
  private _serviceRequestClass: IServiceRequestClass;
  isSaving: boolean;

  constructor(private serviceRequestClassService: ServiceRequestClassService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceRequestClass }) => {
      this.serviceRequestClass = serviceRequestClass;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.serviceRequestClass.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceRequestClassService.update(this.serviceRequestClass));
    } else {
      this.subscribeToSaveResponse(this.serviceRequestClassService.create(this.serviceRequestClass));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IServiceRequestClass>>) {
    result.subscribe((res: HttpResponse<IServiceRequestClass>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
  get serviceRequestClass() {
    return this._serviceRequestClass;
  }

  set serviceRequestClass(serviceRequestClass: IServiceRequestClass) {
    this._serviceRequestClass = serviceRequestClass;
  }
}
