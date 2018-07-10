import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITechnologyClass } from 'app/shared/model/technology-class.model';

type EntityResponseType = HttpResponse<ITechnologyClass>;
type EntityArrayResponseType = HttpResponse<ITechnologyClass[]>;

@Injectable({ providedIn: 'root' })
export class TechnologyClassService {
  private resourceUrl = SERVER_API_URL + 'api/technology-classes';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/technology-classes';

  constructor(private http: HttpClient) {}

  create(technologyClass: ITechnologyClass): Observable<EntityResponseType> {
    return this.http.post<ITechnologyClass>(this.resourceUrl, technologyClass, { observe: 'response' });
  }

  update(technologyClass: ITechnologyClass): Observable<EntityResponseType> {
    return this.http.put<ITechnologyClass>(this.resourceUrl, technologyClass, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITechnologyClass>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITechnologyClass[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITechnologyClass[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
