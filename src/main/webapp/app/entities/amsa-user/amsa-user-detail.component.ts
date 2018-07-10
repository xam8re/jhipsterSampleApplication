import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAMSAUser } from 'app/shared/model/amsa-user.model';

@Component({
  selector: 'jhi-amsa-user-detail',
  templateUrl: './amsa-user-detail.component.html'
})
export class AMSAUserDetailComponent implements OnInit {
  aMSAUser: IAMSAUser;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aMSAUser }) => {
      this.aMSAUser = aMSAUser;
    });
  }

  previousState() {
    window.history.back();
  }
}
