import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDimension } from 'app/shared/model/dimension.model';
import { DimensionService } from './dimension.service';

@Component({
  selector: 'jhi-dimension-update',
  templateUrl: './dimension-update.component.html'
})
export class DimensionUpdateComponent implements OnInit {
  private _dimension: IDimension;
  isSaving: boolean;

  constructor(private dimensionService: DimensionService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dimension }) => {
      this.dimension = dimension;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.dimension.id !== undefined) {
      this.subscribeToSaveResponse(this.dimensionService.update(this.dimension));
    } else {
      this.subscribeToSaveResponse(this.dimensionService.create(this.dimension));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IDimension>>) {
    result.subscribe((res: HttpResponse<IDimension>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
  get dimension() {
    return this._dimension;
  }

  set dimension(dimension: IDimension) {
    this._dimension = dimension;
  }
}
