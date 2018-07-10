import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceRequestClass } from 'app/shared/model/service-request-class.model';

type EntityResponseType = HttpResponse<IServiceRequestClass>;
type EntityArrayResponseType = HttpResponse<IServiceRequestClass[]>;

@Injectable({ providedIn: 'root' })
export class ServiceRequestClassService {
  private resourceUrl = SERVER_API_URL + 'api/service-request-classes';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/service-request-classes';

  constructor(private http: HttpClient) {}

  create(serviceRequestClass: IServiceRequestClass): Observable<EntityResponseType> {
    return this.http.post<IServiceRequestClass>(this.resourceUrl, serviceRequestClass, { observe: 'response' });
  }

  update(serviceRequestClass: IServiceRequestClass): Observable<EntityResponseType> {
    return this.http.put<IServiceRequestClass>(this.resourceUrl, serviceRequestClass, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServiceRequestClass>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceRequestClass[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceRequestClass[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
