import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITaskRequest } from 'app/shared/model/task-request.model';

type EntityResponseType = HttpResponse<ITaskRequest>;
type EntityArrayResponseType = HttpResponse<ITaskRequest[]>;

@Injectable({ providedIn: 'root' })
export class TaskRequestService {
  private resourceUrl = SERVER_API_URL + 'api/task-requests';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/task-requests';

  constructor(private http: HttpClient) {}

  create(taskRequest: ITaskRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskRequest);
    return this.http
      .post<ITaskRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taskRequest: ITaskRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskRequest);
    return this.http
      .put<ITaskRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaskRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaskRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaskRequest[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  private convertDateFromClient(taskRequest: ITaskRequest): ITaskRequest {
    const copy: ITaskRequest = Object.assign({}, taskRequest, {
      period: taskRequest.period != null && taskRequest.period.isValid() ? taskRequest.period.toJSON() : null
    });
    return copy;
  }

  private convertDateFromServer(res: EntityResponseType): EntityResponseType {
    res.body.period = res.body.period != null ? moment(res.body.period) : null;
    return res;
  }

  private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    res.body.forEach((taskRequest: ITaskRequest) => {
      taskRequest.period = taskRequest.period != null ? moment(taskRequest.period) : null;
    });
    return res;
  }
}
