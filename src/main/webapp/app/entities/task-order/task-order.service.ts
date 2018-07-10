import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITaskOrder } from 'app/shared/model/task-order.model';

type EntityResponseType = HttpResponse<ITaskOrder>;
type EntityArrayResponseType = HttpResponse<ITaskOrder[]>;

@Injectable({ providedIn: 'root' })
export class TaskOrderService {
  private resourceUrl = SERVER_API_URL + 'api/task-orders';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/task-orders';

  constructor(private http: HttpClient) {}

  create(taskOrder: ITaskOrder): Observable<EntityResponseType> {
    return this.http.post<ITaskOrder>(this.resourceUrl, taskOrder, { observe: 'response' });
  }

  update(taskOrder: ITaskOrder): Observable<EntityResponseType> {
    return this.http.put<ITaskOrder>(this.resourceUrl, taskOrder, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaskOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaskOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaskOrder[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
