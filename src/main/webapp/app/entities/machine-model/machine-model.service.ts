import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMachineModel } from 'app/shared/model/machine-model.model';

type EntityResponseType = HttpResponse<IMachineModel>;
type EntityArrayResponseType = HttpResponse<IMachineModel[]>;

@Injectable({ providedIn: 'root' })
export class MachineModelService {
  private resourceUrl = SERVER_API_URL + 'api/machine-models';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/machine-models';

  constructor(private http: HttpClient) {}

  create(machineModel: IMachineModel): Observable<EntityResponseType> {
    return this.http.post<IMachineModel>(this.resourceUrl, machineModel, { observe: 'response' });
  }

  update(machineModel: IMachineModel): Observable<EntityResponseType> {
    return this.http.put<IMachineModel>(this.resourceUrl, machineModel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMachineModel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMachineModel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMachineModel[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
