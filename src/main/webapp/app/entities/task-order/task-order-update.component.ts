import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITaskOrder } from 'app/shared/model/task-order.model';
import { TaskOrderService } from './task-order.service';

@Component({
  selector: 'jhi-task-order-update',
  templateUrl: './task-order-update.component.html'
})
export class TaskOrderUpdateComponent implements OnInit {
  private _taskOrder: ITaskOrder;
  isSaving: boolean;

  constructor(private taskOrderService: TaskOrderService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ taskOrder }) => {
      this.taskOrder = taskOrder;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.taskOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.taskOrderService.update(this.taskOrder));
    } else {
      this.subscribeToSaveResponse(this.taskOrderService.create(this.taskOrder));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ITaskOrder>>) {
    result.subscribe((res: HttpResponse<ITaskOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
  get taskOrder() {
    return this._taskOrder;
  }

  set taskOrder(taskOrder: ITaskOrder) {
    this._taskOrder = taskOrder;
  }
}
