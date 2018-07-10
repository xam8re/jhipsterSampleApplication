import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITechnology } from 'app/shared/model/technology.model';

type EntityResponseType = HttpResponse<ITechnology>;
type EntityArrayResponseType = HttpResponse<ITechnology[]>;

@Injectable({ providedIn: 'root' })
export class TechnologyService {
  private resourceUrl = SERVER_API_URL + 'api/technologies';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/technologies';

  constructor(private http: HttpClient) {}

  create(technology: ITechnology): Observable<EntityResponseType> {
    return this.http.post<ITechnology>(this.resourceUrl, technology, { observe: 'response' });
  }

  update(technology: ITechnology): Observable<EntityResponseType> {
    return this.http.put<ITechnology>(this.resourceUrl, technology, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITechnology>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITechnology[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITechnology[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
