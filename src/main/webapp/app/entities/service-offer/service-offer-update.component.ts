import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IServiceOffer } from 'app/shared/model/service-offer.model';
import { ServiceOfferService } from './service-offer.service';
import { IServiceRequest } from 'app/shared/model/service-request.model';
import { ServiceRequestService } from 'app/entities/service-request';
import { IAMSAUser } from 'app/shared/model/amsa-user.model';
import { AMSAUserService } from 'app/entities/amsa-user';

@Component({
  selector: 'jhi-service-offer-update',
  templateUrl: './service-offer-update.component.html'
})
export class ServiceOfferUpdateComponent implements OnInit {
  private _serviceOffer: IServiceOffer;
  isSaving: boolean;

  servicerequests: IServiceRequest[];

  amsausers: IAMSAUser[];
  period: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private serviceOfferService: ServiceOfferService,
    private serviceRequestService: ServiceRequestService,
    private aMSAUserService: AMSAUserService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceOffer }) => {
      this.serviceOffer = serviceOffer;
    });
    this.serviceRequestService.query().subscribe(
      (res: HttpResponse<IServiceRequest[]>) => {
        this.servicerequests = res.body;
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
    this.serviceOffer.period = moment(this.period, DATE_TIME_FORMAT);
    if (this.serviceOffer.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceOfferService.update(this.serviceOffer));
    } else {
      this.subscribeToSaveResponse(this.serviceOfferService.create(this.serviceOffer));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IServiceOffer>>) {
    result.subscribe((res: HttpResponse<IServiceOffer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackAMSAUserById(index: number, item: IAMSAUser) {
    return item.id;
  }
  get serviceOffer() {
    return this._serviceOffer;
  }

  set serviceOffer(serviceOffer: IServiceOffer) {
    this._serviceOffer = serviceOffer;
    this.period = moment(serviceOffer.period).format(DATE_TIME_FORMAT);
  }
}
