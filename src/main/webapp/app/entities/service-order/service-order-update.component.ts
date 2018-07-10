import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IServiceOrder } from 'app/shared/model/service-order.model';
import { ServiceOrderService } from './service-order.service';

@Component({
  selector: 'jhi-service-order-update',
  templateUrl: './service-order-update.component.html'
})
export class ServiceOrderUpdateComponent implements OnInit {
  private _serviceOrder: IServiceOrder;
  isSaving: boolean;

  constructor(private serviceOrderService: ServiceOrderService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceOrder }) => {
      this.serviceOrder = serviceOrder;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.serviceOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceOrderService.update(this.serviceOrder));
    } else {
      this.subscribeToSaveResponse(this.serviceOrderService.create(this.serviceOrder));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IServiceOrder>>) {
    result.subscribe((res: HttpResponse<IServiceOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
  get serviceOrder() {
    return this._serviceOrder;
  }

  set serviceOrder(serviceOrder: IServiceOrder) {
    this._serviceOrder = serviceOrder;
  }
}
