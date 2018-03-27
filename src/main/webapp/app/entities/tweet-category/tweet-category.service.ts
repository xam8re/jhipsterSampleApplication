import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TweetCategory } from './tweet-category.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TweetCategory>;

@Injectable()
export class TweetCategoryService {

    private resourceUrl =  SERVER_API_URL + 'api/tweet-categories';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/tweet-categories';

    constructor(private http: HttpClient) { }

    create(tweetCategory: TweetCategory): Observable<EntityResponseType> {
        const copy = this.convert(tweetCategory);
        return this.http.post<TweetCategory>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tweetCategory: TweetCategory): Observable<EntityResponseType> {
        const copy = this.convert(tweetCategory);
        return this.http.put<TweetCategory>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TweetCategory>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TweetCategory[]>> {
        const options = createRequestOption(req);
        return this.http.get<TweetCategory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TweetCategory[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<TweetCategory[]>> {
        const options = createRequestOption(req);
        return this.http.get<TweetCategory[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TweetCategory[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TweetCategory = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TweetCategory[]>): HttpResponse<TweetCategory[]> {
        const jsonResponse: TweetCategory[] = res.body;
        const body: TweetCategory[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TweetCategory.
     */
    private convertItemFromServer(tweetCategory: TweetCategory): TweetCategory {
        const copy: TweetCategory = Object.assign({}, tweetCategory);
        return copy;
    }

    /**
     * Convert a TweetCategory to a JSON which can be sent to the server.
     */
    private convert(tweetCategory: TweetCategory): TweetCategory {
        const copy: TweetCategory = Object.assign({}, tweetCategory);
        return copy;
    }
}
