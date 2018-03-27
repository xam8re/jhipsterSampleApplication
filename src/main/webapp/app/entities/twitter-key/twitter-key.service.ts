import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TwitterKey } from './twitter-key.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TwitterKey>;

@Injectable()
export class TwitterKeyService {

    private resourceUrl =  SERVER_API_URL + 'api/twitter-keys';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/twitter-keys';

    constructor(private http: HttpClient) { }

    create(twitterKey: TwitterKey): Observable<EntityResponseType> {
        const copy = this.convert(twitterKey);
        return this.http.post<TwitterKey>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(twitterKey: TwitterKey): Observable<EntityResponseType> {
        const copy = this.convert(twitterKey);
        return this.http.put<TwitterKey>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TwitterKey>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TwitterKey[]>> {
        const options = createRequestOption(req);
        return this.http.get<TwitterKey[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TwitterKey[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<TwitterKey[]>> {
        const options = createRequestOption(req);
        return this.http.get<TwitterKey[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TwitterKey[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TwitterKey = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TwitterKey[]>): HttpResponse<TwitterKey[]> {
        const jsonResponse: TwitterKey[] = res.body;
        const body: TwitterKey[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TwitterKey.
     */
    private convertItemFromServer(twitterKey: TwitterKey): TwitterKey {
        const copy: TwitterKey = Object.assign({}, twitterKey);
        return copy;
    }

    /**
     * Convert a TwitterKey to a JSON which can be sent to the server.
     */
    private convert(twitterKey: TwitterKey): TwitterKey {
        const copy: TwitterKey = Object.assign({}, twitterKey);
        return copy;
    }
}
