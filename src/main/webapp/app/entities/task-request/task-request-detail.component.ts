import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaskRequest } from 'app/shared/model/task-request.model';

@Component({
  selector: 'jhi-task-request-detail',
  templateUrl: './task-request-detail.component.html'
})
export class TaskRequestDetailComponent implements OnInit {
  taskRequest: ITaskRequest;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ taskRequest }) => {
      this.taskRequest = taskRequest;
    });
  }

  previousState() {
    window.history.back();
  }
}
