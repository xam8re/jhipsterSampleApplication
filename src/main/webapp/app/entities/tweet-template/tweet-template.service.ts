import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TweetTemplate } from './tweet-template.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TweetTemplate>;

@Injectable()
export class TweetTemplateService {

    private resourceUrl =  SERVER_API_URL + 'api/tweet-templates';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/tweet-templates';

    constructor(private http: HttpClient) { }

    create(tweetTemplate: TweetTemplate): Observable<EntityResponseType> {
        const copy = this.convert(tweetTemplate);
        return this.http.post<TweetTemplate>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tweetTemplate: TweetTemplate): Observable<EntityResponseType> {
        const copy = this.convert(tweetTemplate);
        return this.http.put<TweetTemplate>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TweetTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TweetTemplate[]>> {
        const options = createRequestOption(req);
        return this.http.get<TweetTemplate[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TweetTemplate[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<TweetTemplate[]>> {
        const options = createRequestOption(req);
        return this.http.get<TweetTemplate[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TweetTemplate[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TweetTemplate = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TweetTemplate[]>): HttpResponse<TweetTemplate[]> {
        const jsonResponse: TweetTemplate[] = res.body;
        const body: TweetTemplate[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TweetTemplate.
     */
    private convertItemFromServer(tweetTemplate: TweetTemplate): TweetTemplate {
        const copy: TweetTemplate = Object.assign({}, tweetTemplate);
        return copy;
    }

    /**
     * Convert a TweetTemplate to a JSON which can be sent to the server.
     */
    private convert(tweetTemplate: TweetTemplate): TweetTemplate {
        const copy: TweetTemplate = Object.assign({}, tweetTemplate);
        return copy;
    }
}
