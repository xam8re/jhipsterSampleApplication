import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ITaskOffer } from 'app/shared/model/task-offer.model';
import { TaskOfferService } from './task-offer.service';
import { ITaskRequest } from 'app/shared/model/task-request.model';
import { TaskRequestService } from 'app/entities/task-request';
import { IAMSAUser } from 'app/shared/model/amsa-user.model';
import { AMSAUserService } from 'app/entities/amsa-user';
import { IServiceOffer } from 'app/shared/model/service-offer.model';
import { ServiceOfferService } from 'app/entities/service-offer';

@Component({
  selector: 'jhi-task-offer-update',
  templateUrl: './task-offer-update.component.html'
})
export class TaskOfferUpdateComponent implements OnInit {
  private _taskOffer: ITaskOffer;
  isSaving: boolean;

  taskrequests: ITaskRequest[];

  amsausers: IAMSAUser[];

  serviceoffers: IServiceOffer[];
  period: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private taskOfferService: TaskOfferService,
    private taskRequestService: TaskRequestService,
    private aMSAUserService: AMSAUserService,
    private serviceOfferService: ServiceOfferService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ taskOffer }) => {
      this.taskOffer = taskOffer;
    });
    this.taskRequestService.query().subscribe(
      (res: HttpResponse<ITaskRequest[]>) => {
        this.taskrequests = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.aMSAUserService.query().subscribe(
      (res: HttpResponse<IAMSAUser[]>) => {
        this.amsausers = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.serviceOfferService.query().subscribe(
      (res: HttpResponse<IServiceOffer[]>) => {
        this.serviceoffers = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.taskOffer.period = moment(this.period, DATE_TIME_FORMAT);
    if (this.taskOffer.id !== undefined) {
      this.subscribeToSaveResponse(this.taskOfferService.update(this.taskOffer));
    } else {
      this.subscribeToSaveResponse(this.taskOfferService.create(this.taskOffer));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ITaskOffer>>) {
    result.subscribe((res: HttpResponse<ITaskOffer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackTaskRequestById(index: number, item: ITaskRequest) {
    return item.id;
  }

  trackAMSAUserById(index: number, item: IAMSAUser) {
    return item.id;
  }

  trackServiceOfferById(index: number, item: IServiceOffer) {
    return item.id;
  }
  get taskOffer() {
    return this._taskOffer;
  }

  set taskOffer(taskOffer: ITaskOffer) {
    this._taskOffer = taskOffer;
    this.period = moment(taskOffer.period).format(DATE_TIME_FORMAT);
  }
}
