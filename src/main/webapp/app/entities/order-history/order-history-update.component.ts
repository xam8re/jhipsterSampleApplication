import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IOrderHistory } from 'app/shared/model/order-history.model';
import { OrderHistoryService } from './order-history.service';
import { IAMSAUser } from 'app/shared/model/amsa-user.model';
import { AMSAUserService } from 'app/entities/amsa-user';
import { IServiceOrder } from 'app/shared/model/service-order.model';
import { ServiceOrderService } from 'app/entities/service-order';
import { ITaskOrder } from 'app/shared/model/task-order.model';
import { TaskOrderService } from 'app/entities/task-order';

@Component({
  selector: 'jhi-order-history-update',
  templateUrl: './order-history-update.component.html'
})
export class OrderHistoryUpdateComponent implements OnInit {
  private _orderHistory: IOrderHistory;
  isSaving: boolean;

  amsausers: IAMSAUser[];

  serviceorders: IServiceOrder[];

  taskorders: ITaskOrder[];
  date: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private orderHistoryService: OrderHistoryService,
    private aMSAUserService: AMSAUserService,
    private serviceOrderService: ServiceOrderService,
    private taskOrderService: TaskOrderService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ orderHistory }) => {
      this.orderHistory = orderHistory;
    });
    this.aMSAUserService.query().subscribe(
      (res: HttpResponse<IAMSAUser[]>) => {
        this.amsausers = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.serviceOrderService.query().subscribe(
      (res: HttpResponse<IServiceOrder[]>) => {
        this.serviceorders = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.taskOrderService.query().subscribe(
      (res: HttpResponse<ITaskOrder[]>) => {
        this.taskorders = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.orderHistory.date = moment(this.date, DATE_TIME_FORMAT);
    if (this.orderHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.orderHistoryService.update(this.orderHistory));
    } else {
      this.subscribeToSaveResponse(this.orderHistoryService.create(this.orderHistory));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IOrderHistory>>) {
    result.subscribe((res: HttpResponse<IOrderHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackAMSAUserById(index: number, item: IAMSAUser) {
    return item.id;
  }

  trackServiceOrderById(index: number, item: IServiceOrder) {
    return item.id;
  }

  trackTaskOrderById(index: number, item: ITaskOrder) {
    return item.id;
  }
  get orderHistory() {
    return this._orderHistory;
  }

  set orderHistory(orderHistory: IOrderHistory) {
    this._orderHistory = orderHistory;
    this.date = moment(orderHistory.date).format(DATE_TIME_FORMAT);
  }
}
