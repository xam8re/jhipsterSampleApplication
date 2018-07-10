import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaskOrder } from 'app/shared/model/task-order.model';

@Component({
  selector: 'jhi-task-order-detail',
  templateUrl: './task-order-detail.component.html'
})
export class TaskOrderDetailComponent implements OnInit {
  taskOrder: ITaskOrder;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ taskOrder }) => {
      this.taskOrder = taskOrder;
    });
  }

  previousState() {
    window.history.back();
  }
}
