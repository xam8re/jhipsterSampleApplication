import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceRequest } from 'app/shared/model/service-request.model';

type EntityResponseType = HttpResponse<IServiceRequest>;
type EntityArrayResponseType = HttpResponse<IServiceRequest[]>;

@Injectable({ providedIn: 'root' })
export class ServiceRequestService {
  private resourceUrl = SERVER_API_URL + 'api/service-requests';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/service-requests';

  constructor(private http: HttpClient) {}

  create(serviceRequest: IServiceRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceRequest);
    return this.http
      .post<IServiceRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceRequest: IServiceRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceRequest);
    return this.http
      .put<IServiceRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceRequest[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  private convertDateFromClient(serviceRequest: IServiceRequest): IServiceRequest {
    const copy: IServiceRequest = Object.assign({}, serviceRequest, {
      period: serviceRequest.period != null && serviceRequest.period.isValid() ? serviceRequest.period.toJSON() : null
    });
    return copy;
  }

  private convertDateFromServer(res: EntityResponseType): EntityResponseType {
    res.body.period = res.body.period != null ? moment(res.body.period) : null;
    return res;
  }

  private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    res.body.forEach((serviceRequest: IServiceRequest) => {
      serviceRequest.period = serviceRequest.period != null ? moment(serviceRequest.period) : null;
    });
    return res;
  }
}
