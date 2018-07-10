import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocument } from 'app/shared/model/document.model';

type EntityResponseType = HttpResponse<IDocument>;
type EntityArrayResponseType = HttpResponse<IDocument[]>;

@Injectable({ providedIn: 'root' })
export class DocumentService {
  private resourceUrl = SERVER_API_URL + 'api/documents';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/documents';

  constructor(private http: HttpClient) {}

  create(document: IDocument): Observable<EntityResponseType> {
    return this.http.post<IDocument>(this.resourceUrl, document, { observe: 'response' });
  }

  update(document: IDocument): Observable<EntityResponseType> {
    return this.http.put<IDocument>(this.resourceUrl, document, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocument[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocument[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
