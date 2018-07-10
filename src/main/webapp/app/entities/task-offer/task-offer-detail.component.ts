import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaskOffer } from 'app/shared/model/task-offer.model';

@Component({
  selector: 'jhi-task-offer-detail',
  templateUrl: './task-offer-detail.component.html'
})
export class TaskOfferDetailComponent implements OnInit {
  taskOffer: ITaskOffer;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ taskOffer }) => {
      this.taskOffer = taskOffer;
    });
  }

  previousState() {
    window.history.back();
  }
}
