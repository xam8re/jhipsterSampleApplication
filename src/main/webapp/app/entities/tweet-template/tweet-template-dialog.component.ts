import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TweetTemplate } from './tweet-template.model';
import { TweetTemplatePopupService } from './tweet-template-popup.service';
import { TweetTemplateService } from './tweet-template.service';
import { TwitterKey, TwitterKeyService } from '../twitter-key';

@Component({
    selector: 'jhi-tweet-template-dialog',
    templateUrl: './tweet-template-dialog.component.html'
})
export class TweetTemplateDialogComponent implements OnInit {

    tweetTemplate: TweetTemplate;
    isSaving: boolean;

    twitterkeys: TwitterKey[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private tweetTemplateService: TweetTemplateService,
        private twitterKeyService: TwitterKeyService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.twitterKeyService.query()
            .subscribe((res: HttpResponse<TwitterKey[]>) => { this.twitterkeys = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tweetTemplate.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tweetTemplateService.update(this.tweetTemplate));
        } else {
            this.subscribeToSaveResponse(
                this.tweetTemplateService.create(this.tweetTemplate));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TweetTemplate>>) {
        result.subscribe((res: HttpResponse<TweetTemplate>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TweetTemplate) {
        this.eventManager.broadcast({ name: 'tweetTemplateListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTwitterKeyById(index: number, item: TwitterKey) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tweet-template-popup',
    template: ''
})
export class TweetTemplatePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tweetTemplatePopupService: TweetTemplatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tweetTemplatePopupService
                    .open(TweetTemplateDialogComponent as Component, params['id']);
            } else {
                this.tweetTemplatePopupService
                    .open(TweetTemplateDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
