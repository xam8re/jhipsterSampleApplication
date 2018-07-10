import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceRequestClass } from 'app/shared/model/service-request-class.model';

@Component({
  selector: 'jhi-service-request-class-detail',
  templateUrl: './service-request-class-detail.component.html'
})
export class ServiceRequestClassDetailComponent implements OnInit {
  serviceRequestClass: IServiceRequestClass;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceRequestClass }) => {
      this.serviceRequestClass = serviceRequestClass;
    });
  }

  previousState() {
    window.history.back();
  }
}
