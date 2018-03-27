import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Tweet } from './tweet.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Tweet>;

@Injectable()
export class TweetService {

    private resourceUrl =  SERVER_API_URL + 'api/tweets';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/tweets';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(tweet: Tweet): Observable<EntityResponseType> {
        const copy = this.convert(tweet);
        return this.http.post<Tweet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tweet: Tweet): Observable<EntityResponseType> {
        const copy = this.convert(tweet);
        return this.http.put<Tweet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Tweet>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Tweet[]>> {
        const options = createRequestOption(req);
        return this.http.get<Tweet[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Tweet[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Tweet[]>> {
        const options = createRequestOption(req);
        return this.http.get<Tweet[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Tweet[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Tweet = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Tweet[]>): HttpResponse<Tweet[]> {
        const jsonResponse: Tweet[] = res.body;
        const body: Tweet[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Tweet.
     */
    private convertItemFromServer(tweet: Tweet): Tweet {
        const copy: Tweet = Object.assign({}, tweet);
        copy.tweetData = this.dateUtils
            .convertDateTimeFromServer(tweet.tweetData);
        return copy;
    }

    /**
     * Convert a Tweet to a JSON which can be sent to the server.
     */
    private convert(tweet: Tweet): Tweet {
        const copy: Tweet = Object.assign({}, tweet);

        copy.tweetData = this.dateUtils.toDate(tweet.tweetData);
        return copy;
    }
}
