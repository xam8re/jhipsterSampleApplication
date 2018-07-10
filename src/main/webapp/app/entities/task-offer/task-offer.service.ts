import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITaskOffer } from 'app/shared/model/task-offer.model';

type EntityResponseType = HttpResponse<ITaskOffer>;
type EntityArrayResponseType = HttpResponse<ITaskOffer[]>;

@Injectable({ providedIn: 'root' })
export class TaskOfferService {
  private resourceUrl = SERVER_API_URL + 'api/task-offers';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/task-offers';

  constructor(private http: HttpClient) {}

  create(taskOffer: ITaskOffer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskOffer);
    return this.http
      .post<ITaskOffer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taskOffer: ITaskOffer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskOffer);
    return this.http
      .put<ITaskOffer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaskOffer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaskOffer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaskOffer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  private convertDateFromClient(taskOffer: ITaskOffer): ITaskOffer {
    const copy: ITaskOffer = Object.assign({}, taskOffer, {
      period: taskOffer.period != null && taskOffer.period.isValid() ? taskOffer.period.toJSON() : null
    });
    return copy;
  }

  private convertDateFromServer(res: EntityResponseType): EntityResponseType {
    res.body.period = res.body.period != null ? moment(res.body.period) : null;
    return res;
  }

  private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    res.body.forEach((taskOffer: ITaskOffer) => {
      taskOffer.period = taskOffer.period != null ? moment(taskOffer.period) : null;
    });
    return res;
  }
}
