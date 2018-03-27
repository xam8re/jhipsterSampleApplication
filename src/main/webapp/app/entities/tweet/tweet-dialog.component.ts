import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tweet } from './tweet.model';
import { TweetPopupService } from './tweet-popup.service';
import { TweetService } from './tweet.service';
import { TweetCategory, TweetCategoryService } from '../tweet-category';
import { TweetTemplate, TweetTemplateService } from '../tweet-template';
import { Job, JobService } from '../job';

@Component({
    selector: 'jhi-tweet-dialog',
    templateUrl: './tweet-dialog.component.html'
})
export class TweetDialogComponent implements OnInit {

    tweet: Tweet;
    isSaving: boolean;

    tweetcategories: TweetCategory[];

    tweettemplates: TweetTemplate[];

    jobs: Job[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private tweetService: TweetService,
        private tweetCategoryService: TweetCategoryService,
        private tweetTemplateService: TweetTemplateService,
        private jobService: JobService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tweetCategoryService.query()
            .subscribe((res: HttpResponse<TweetCategory[]>) => { this.tweetcategories = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tweetTemplateService.query()
            .subscribe((res: HttpResponse<TweetTemplate[]>) => { this.tweettemplates = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.jobService.query()
            .subscribe((res: HttpResponse<Job[]>) => { this.jobs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tweet.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tweetService.update(this.tweet));
        } else {
            this.subscribeToSaveResponse(
                this.tweetService.create(this.tweet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Tweet>>) {
        result.subscribe((res: HttpResponse<Tweet>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Tweet) {
        this.eventManager.broadcast({ name: 'tweetListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTweetCategoryById(index: number, item: TweetCategory) {
        return item.id;
    }

    trackTweetTemplateById(index: number, item: TweetTemplate) {
        return item.id;
    }

    trackJobById(index: number, item: Job) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tweet-popup',
    template: ''
})
export class TweetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tweetPopupService: TweetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tweetPopupService
                    .open(TweetDialogComponent as Component, params['id']);
            } else {
                this.tweetPopupService
                    .open(TweetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
