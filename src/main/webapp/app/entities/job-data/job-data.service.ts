import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JobData } from './job-data.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<JobData>;

@Injectable()
export class JobDataService {

    private resourceUrl =  SERVER_API_URL + 'api/job-data';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/job-data';

    constructor(private http: HttpClient) { }

    create(jobData: JobData): Observable<EntityResponseType> {
        const copy = this.convert(jobData);
        return this.http.post<JobData>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(jobData: JobData): Observable<EntityResponseType> {
        const copy = this.convert(jobData);
        return this.http.put<JobData>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<JobData>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<JobData[]>> {
        const options = createRequestOption(req);
        return this.http.get<JobData[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<JobData[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<JobData[]>> {
        const options = createRequestOption(req);
        return this.http.get<JobData[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<JobData[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: JobData = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<JobData[]>): HttpResponse<JobData[]> {
        const jsonResponse: JobData[] = res.body;
        const body: JobData[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to JobData.
     */
    private convertItemFromServer(jobData: JobData): JobData {
        const copy: JobData = Object.assign({}, jobData);
        return copy;
    }

    /**
     * Convert a JobData to a JSON which can be sent to the server.
     */
    private convert(jobData: JobData): JobData {
        const copy: JobData = Object.assign({}, jobData);
        return copy;
    }
}
