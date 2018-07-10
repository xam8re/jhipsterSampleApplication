import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMaterial } from 'app/shared/model/material.model';
import { MaterialService } from './material.service';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document';
import { IResource } from 'app/shared/model/resource.model';
import { ResourceService } from 'app/entities/resource';

@Component({
  selector: 'jhi-material-update',
  templateUrl: './material-update.component.html'
})
export class MaterialUpdateComponent implements OnInit {
  private _material: IMaterial;
  isSaving: boolean;

  images: IDocument[];

  resources: IResource[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private materialService: MaterialService,
    private documentService: DocumentService,
    private resourceService: ResourceService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ material }) => {
      this.material = material;
    });
    this.documentService.query({ filter: 'material-is-null' }).subscribe(
      (res: HttpResponse<IDocument[]>) => {
        if (!this.material.imageId) {
          this.images = res.body;
        } else {
          this.documentService.find(this.material.imageId).subscribe(
            (subRes: HttpResponse<IDocument>) => {
              this.images = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.resourceService.query().subscribe(
      (res: HttpResponse<IResource[]>) => {
        this.resources = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.material.id !== undefined) {
      this.subscribeToSaveResponse(this.materialService.update(this.material));
    } else {
      this.subscribeToSaveResponse(this.materialService.create(this.material));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IMaterial>>) {
    result.subscribe((res: HttpResponse<IMaterial>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackDocumentById(index: number, item: IDocument) {
    return item.id;
  }

  trackResourceById(index: number, item: IResource) {
    return item.id;
  }
  get material() {
    return this._material;
  }

  set material(material: IMaterial) {
    this._material = material;
  }
}
