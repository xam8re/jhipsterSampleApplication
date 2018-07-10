import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAMSAUser } from 'app/shared/model/amsa-user.model';

type EntityResponseType = HttpResponse<IAMSAUser>;
type EntityArrayResponseType = HttpResponse<IAMSAUser[]>;

@Injectable({ providedIn: 'root' })
export class AMSAUserService {
  private resourceUrl = SERVER_API_URL + 'api/amsa-users';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/amsa-users';

  constructor(private http: HttpClient) {}

  create(aMSAUser: IAMSAUser): Observable<EntityResponseType> {
    return this.http.post<IAMSAUser>(this.resourceUrl, aMSAUser, { observe: 'response' });
  }

  update(aMSAUser: IAMSAUser): Observable<EntityResponseType> {
    return this.http.put<IAMSAUser>(this.resourceUrl, aMSAUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAMSAUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAMSAUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAMSAUser[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
