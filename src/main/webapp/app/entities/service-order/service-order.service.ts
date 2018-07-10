import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceOrder } from 'app/shared/model/service-order.model';

type EntityResponseType = HttpResponse<IServiceOrder>;
type EntityArrayResponseType = HttpResponse<IServiceOrder[]>;

@Injectable({ providedIn: 'root' })
export class ServiceOrderService {
  private resourceUrl = SERVER_API_URL + 'api/service-orders';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/service-orders';

  constructor(private http: HttpClient) {}

  create(serviceOrder: IServiceOrder): Observable<EntityResponseType> {
    return this.http.post<IServiceOrder>(this.resourceUrl, serviceOrder, { observe: 'response' });
  }

  update(serviceOrder: IServiceOrder): Observable<EntityResponseType> {
    return this.http.put<IServiceOrder>(this.resourceUrl, serviceOrder, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServiceOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceOrder[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
