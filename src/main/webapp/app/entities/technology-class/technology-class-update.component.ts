import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITechnologyClass } from 'app/shared/model/technology-class.model';
import { TechnologyClassService } from './technology-class.service';

@Component({
  selector: 'jhi-technology-class-update',
  templateUrl: './technology-class-update.component.html'
})
export class TechnologyClassUpdateComponent implements OnInit {
  private _technologyClass: ITechnologyClass;
  isSaving: boolean;

  constructor(private technologyClassService: TechnologyClassService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ technologyClass }) => {
      this.technologyClass = technologyClass;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.technologyClass.id !== undefined) {
      this.subscribeToSaveResponse(this.technologyClassService.update(this.technologyClass));
    } else {
      this.subscribeToSaveResponse(this.technologyClassService.create(this.technologyClass));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ITechnologyClass>>) {
    result.subscribe((res: HttpResponse<ITechnologyClass>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
  get technologyClass() {
    return this._technologyClass;
  }

  set technologyClass(technologyClass: ITechnologyClass) {
    this._technologyClass = technologyClass;
  }
}
