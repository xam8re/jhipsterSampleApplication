import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { HodKey } from './hod-key.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<HodKey>;

@Injectable()
export class HodKeyService {

    private resourceUrl =  SERVER_API_URL + 'api/hod-keys';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/hod-keys';

    constructor(private http: HttpClient) { }

    create(hodKey: HodKey): Observable<EntityResponseType> {
        const copy = this.convert(hodKey);
        return this.http.post<HodKey>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(hodKey: HodKey): Observable<EntityResponseType> {
        const copy = this.convert(hodKey);
        return this.http.put<HodKey>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<HodKey>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<HodKey[]>> {
        const options = createRequestOption(req);
        return this.http.get<HodKey[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HodKey[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<HodKey[]>> {
        const options = createRequestOption(req);
        return this.http.get<HodKey[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HodKey[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: HodKey = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<HodKey[]>): HttpResponse<HodKey[]> {
        const jsonResponse: HodKey[] = res.body;
        const body: HodKey[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to HodKey.
     */
    private convertItemFromServer(hodKey: HodKey): HodKey {
        const copy: HodKey = Object.assign({}, hodKey);
        return copy;
    }

    /**
     * Convert a HodKey to a JSON which can be sent to the server.
     */
    private convert(hodKey: HodKey): HodKey {
        const copy: HodKey = Object.assign({}, hodKey);
        return copy;
    }
}
