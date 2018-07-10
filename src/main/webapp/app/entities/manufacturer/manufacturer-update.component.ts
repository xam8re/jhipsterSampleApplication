import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerService } from './manufacturer.service';

@Component({
  selector: 'jhi-manufacturer-update',
  templateUrl: './manufacturer-update.component.html'
})
export class ManufacturerUpdateComponent implements OnInit {
  private _manufacturer: IManufacturer;
  isSaving: boolean;

  constructor(private manufacturerService: ManufacturerService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ manufacturer }) => {
      this.manufacturer = manufacturer;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.manufacturer.id !== undefined) {
      this.subscribeToSaveResponse(this.manufacturerService.update(this.manufacturer));
    } else {
      this.subscribeToSaveResponse(this.manufacturerService.create(this.manufacturer));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IManufacturer>>) {
    result.subscribe((res: HttpResponse<IManufacturer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
  get manufacturer() {
    return this._manufacturer;
  }

  set manufacturer(manufacturer: IManufacturer) {
    this._manufacturer = manufacturer;
  }
}
