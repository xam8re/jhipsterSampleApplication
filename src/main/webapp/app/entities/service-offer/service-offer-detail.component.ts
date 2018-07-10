import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceOffer } from 'app/shared/model/service-offer.model';

@Component({
  selector: 'jhi-service-offer-detail',
  templateUrl: './service-offer-detail.component.html'
})
export class ServiceOfferDetailComponent implements OnInit {
  serviceOffer: IServiceOffer;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceOffer }) => {
      this.serviceOffer = serviceOffer;
    });
  }

  previousState() {
    window.history.back();
  }
}
