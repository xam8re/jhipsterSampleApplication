import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITechnology } from 'app/shared/model/technology.model';
import { TechnologyService } from './technology.service';
import { ITechnologyClass } from 'app/shared/model/technology-class.model';
import { TechnologyClassService } from 'app/entities/technology-class';
import { IMachineModel } from 'app/shared/model/machine-model.model';
import { MachineModelService } from 'app/entities/machine-model';

@Component({
  selector: 'jhi-technology-update',
  templateUrl: './technology-update.component.html'
})
export class TechnologyUpdateComponent implements OnInit {
  private _technology: ITechnology;
  isSaving: boolean;

  technologyclasses: ITechnologyClass[];

  machinemodels: IMachineModel[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private technologyService: TechnologyService,
    private technologyClassService: TechnologyClassService,
    private machineModelService: MachineModelService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ technology }) => {
      this.technology = technology;
    });
    this.technologyClassService.query({ filter: 'technology-is-null' }).subscribe(
      (res: HttpResponse<ITechnologyClass[]>) => {
        if (!this.technology.technologyClassId) {
          this.technologyclasses = res.body;
        } else {
          this.technologyClassService.find(this.technology.technologyClassId).subscribe(
            (subRes: HttpResponse<ITechnologyClass>) => {
              this.technologyclasses = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.machineModelService.query().subscribe(
      (res: HttpResponse<IMachineModel[]>) => {
        this.machinemodels = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.technology.id !== undefined) {
      this.subscribeToSaveResponse(this.technologyService.update(this.technology));
    } else {
      this.subscribeToSaveResponse(this.technologyService.create(this.technology));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ITechnology>>) {
    result.subscribe((res: HttpResponse<ITechnology>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTechnologyClassById(index: number, item: ITechnologyClass) {
    return item.id;
  }

  trackMachineModelById(index: number, item: IMachineModel) {
    return item.id;
  }
  get technology() {
    return this._technology;
  }

  set technology(technology: ITechnology) {
    this._technology = technology;
  }
}
