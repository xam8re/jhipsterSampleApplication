import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { JobData } from './job-data.model';
import { JobDataService } from './job-data.service';

@Component({
    selector: 'jhi-job-data-detail',
    templateUrl: './job-data-detail.component.html'
})
export class JobDataDetailComponent implements OnInit, OnDestroy {

    jobData: JobData;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private jobDataService: JobDataService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInJobData();
    }

    load(id) {
        this.jobDataService.find(id)
            .subscribe((jobDataResponse: HttpResponse<JobData>) => {
                this.jobData = jobDataResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInJobData() {
        this.eventSubscriber = this.eventManager.subscribe(
            'jobDataListModification',
            (response) => this.load(this.jobData.id)
        );
    }
}
