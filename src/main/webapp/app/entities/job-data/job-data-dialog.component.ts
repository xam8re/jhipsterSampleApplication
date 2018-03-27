import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { JobData } from './job-data.model';
import { JobDataPopupService } from './job-data-popup.service';
import { JobDataService } from './job-data.service';
import { Tweet, TweetService } from '../tweet';

@Component({
    selector: 'jhi-job-data-dialog',
    templateUrl: './job-data-dialog.component.html'
})
export class JobDataDialogComponent implements OnInit {

    jobData: JobData;
    isSaving: boolean;

    tweets: Tweet[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private jobDataService: JobDataService,
        private tweetService: TweetService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tweetService.query()
            .subscribe((res: HttpResponse<Tweet[]>) => { this.tweets = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.jobData.id !== undefined) {
            this.subscribeToSaveResponse(
                this.jobDataService.update(this.jobData));
        } else {
            this.subscribeToSaveResponse(
                this.jobDataService.create(this.jobData));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<JobData>>) {
        result.subscribe((res: HttpResponse<JobData>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: JobData) {
        this.eventManager.broadcast({ name: 'jobDataListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTweetById(index: number, item: Tweet) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-job-data-popup',
    template: ''
})
export class JobDataPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobDataPopupService: JobDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.jobDataPopupService
                    .open(JobDataDialogComponent as Component, params['id']);
            } else {
                this.jobDataPopupService
                    .open(JobDataDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
