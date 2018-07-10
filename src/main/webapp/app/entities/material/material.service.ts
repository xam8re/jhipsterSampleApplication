import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMaterial } from 'app/shared/model/material.model';

type EntityResponseType = HttpResponse<IMaterial>;
type EntityArrayResponseType = HttpResponse<IMaterial[]>;

@Injectable({ providedIn: 'root' })
export class MaterialService {
  private resourceUrl = SERVER_API_URL + 'api/materials';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/materials';

  constructor(private http: HttpClient) {}

  create(material: IMaterial): Observable<EntityResponseType> {
    return this.http.post<IMaterial>(this.resourceUrl, material, { observe: 'response' });
  }

  update(material: IMaterial): Observable<EntityResponseType> {
    return this.http.put<IMaterial>(this.resourceUrl, material, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMaterial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMaterial[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMaterial[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
