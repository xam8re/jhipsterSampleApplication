import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceOrder } from 'app/shared/model/service-order.model';

@Component({
  selector: 'jhi-service-order-detail',
  templateUrl: './service-order-detail.component.html'
})
export class ServiceOrderDetailComponent implements OnInit {
  serviceOrder: IServiceOrder;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceOrder }) => {
      this.serviceOrder = serviceOrder;
    });
  }

  previousState() {
    window.history.back();
  }
}
