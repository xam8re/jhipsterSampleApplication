import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from './document.service';

@Component({
  selector: 'jhi-document-update',
  templateUrl: './document-update.component.html'
})
export class DocumentUpdateComponent implements OnInit {
  private _document: IDocument;
  isSaving: boolean;

  constructor(private documentService: DocumentService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ document }) => {
      this.document = document;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.document.id !== undefined) {
      this.subscribeToSaveResponse(this.documentService.update(this.document));
    } else {
      this.subscribeToSaveResponse(this.documentService.create(this.document));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IDocument>>) {
    result.subscribe((res: HttpResponse<IDocument>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
  get document() {
    return this._document;
  }

  set document(document: IDocument) {
    this._document = document;
  }
}
