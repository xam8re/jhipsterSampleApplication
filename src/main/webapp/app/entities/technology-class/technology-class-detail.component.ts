import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITechnologyClass } from 'app/shared/model/technology-class.model';

@Component({
  selector: 'jhi-technology-class-detail',
  templateUrl: './technology-class-detail.component.html'
})
export class TechnologyClassDetailComponent implements OnInit {
  technologyClass: ITechnologyClass;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ technologyClass }) => {
      this.technologyClass = technologyClass;
    });
  }

  previousState() {
    window.history.back();
  }
}
