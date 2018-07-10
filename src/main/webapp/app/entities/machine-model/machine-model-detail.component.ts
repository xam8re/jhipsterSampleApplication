import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMachineModel } from 'app/shared/model/machine-model.model';

@Component({
  selector: 'jhi-machine-model-detail',
  templateUrl: './machine-model-detail.component.html'
})
export class MachineModelDetailComponent implements OnInit {
  machineModel: IMachineModel;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ machineModel }) => {
      this.machineModel = machineModel;
    });
  }

  previousState() {
    window.history.back();
  }
}
