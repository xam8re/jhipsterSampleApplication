import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceRequest } from 'app/shared/model/service-request.model';

@Component({
  selector: 'jhi-service-request-detail',
  templateUrl: './service-request-detail.component.html'
})
export class ServiceRequestDetailComponent implements OnInit {
  serviceRequest: IServiceRequest;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceRequest }) => {
      this.serviceRequest = serviceRequest;
    });
  }

  previousState() {
    window.history.back();
  }
}
