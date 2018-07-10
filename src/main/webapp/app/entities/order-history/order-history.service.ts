import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderHistory } from 'app/shared/model/order-history.model';

type EntityResponseType = HttpResponse<IOrderHistory>;
type EntityArrayResponseType = HttpResponse<IOrderHistory[]>;

@Injectable({ providedIn: 'root' })
export class OrderHistoryService {
  private resourceUrl = SERVER_API_URL + 'api/order-histories';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/order-histories';

  constructor(private http: HttpClient) {}

  create(orderHistory: IOrderHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderHistory);
    return this.http
      .post<IOrderHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orderHistory: IOrderHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderHistory);
    return this.http
      .put<IOrderHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrderHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderHistory[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  private convertDateFromClient(orderHistory: IOrderHistory): IOrderHistory {
    const copy: IOrderHistory = Object.assign({}, orderHistory, {
      date: orderHistory.date != null && orderHistory.date.isValid() ? orderHistory.date.toJSON() : null
    });
    return copy;
  }

  private convertDateFromServer(res: EntityResponseType): EntityResponseType {
    res.body.date = res.body.date != null ? moment(res.body.date) : null;
    return res;
  }

  private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    res.body.forEach((orderHistory: IOrderHistory) => {
      orderHistory.date = orderHistory.date != null ? moment(orderHistory.date) : null;
    });
    return res;
  }
}
