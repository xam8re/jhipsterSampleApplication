import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TweetCategory } from './tweet-category.model';
import { TweetCategoryPopupService } from './tweet-category-popup.service';
import { TweetCategoryService } from './tweet-category.service';

@Component({
    selector: 'jhi-tweet-category-dialog',
    templateUrl: './tweet-category-dialog.component.html'
})
export class TweetCategoryDialogComponent implements OnInit {

    tweetCategory: TweetCategory;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private tweetCategoryService: TweetCategoryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tweetCategory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tweetCategoryService.update(this.tweetCategory));
        } else {
            this.subscribeToSaveResponse(
                this.tweetCategoryService.create(this.tweetCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TweetCategory>>) {
        result.subscribe((res: HttpResponse<TweetCategory>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TweetCategory) {
        this.eventManager.broadcast({ name: 'tweetCategoryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-tweet-category-popup',
    template: ''
})
export class TweetCategoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tweetCategoryPopupService: TweetCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tweetCategoryPopupService
                    .open(TweetCategoryDialogComponent as Component, params['id']);
            } else {
                this.tweetCategoryPopupService
                    .open(TweetCategoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
