import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceOffer } from 'app/shared/model/service-offer.model';

type EntityResponseType = HttpResponse<IServiceOffer>;
type EntityArrayResponseType = HttpResponse<IServiceOffer[]>;

@Injectable({ providedIn: 'root' })
export class ServiceOfferService {
  private resourceUrl = SERVER_API_URL + 'api/service-offers';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/service-offers';

  constructor(private http: HttpClient) {}

  create(serviceOffer: IServiceOffer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceOffer);
    return this.http
      .post<IServiceOffer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceOffer: IServiceOffer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceOffer);
    return this.http
      .put<IServiceOffer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceOffer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceOffer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceOffer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  private convertDateFromClient(serviceOffer: IServiceOffer): IServiceOffer {
    const copy: IServiceOffer = Object.assign({}, serviceOffer, {
      period: serviceOffer.period != null && serviceOffer.period.isValid() ? serviceOffer.period.toJSON() : null
    });
    return copy;
  }

  private convertDateFromServer(res: EntityResponseType): EntityResponseType {
    res.body.period = res.body.period != null ? moment(res.body.period) : null;
    return res;
  }

  private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    res.body.forEach((serviceOffer: IServiceOffer) => {
      serviceOffer.period = serviceOffer.period != null ? moment(serviceOffer.period) : null;
    });
    return res;
  }
}
