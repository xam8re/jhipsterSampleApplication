import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TwitterKey } from './twitter-key.model';
import { TwitterKeyPopupService } from './twitter-key-popup.service';
import { TwitterKeyService } from './twitter-key.service';

@Component({
    selector: 'jhi-twitter-key-dialog',
    templateUrl: './twitter-key-dialog.component.html'
})
export class TwitterKeyDialogComponent implements OnInit {

    twitterKey: TwitterKey;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private twitterKeyService: TwitterKeyService,
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
        if (this.twitterKey.id !== undefined) {
            this.subscribeToSaveResponse(
                this.twitterKeyService.update(this.twitterKey));
        } else {
            this.subscribeToSaveResponse(
                this.twitterKeyService.create(this.twitterKey));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TwitterKey>>) {
        result.subscribe((res: HttpResponse<TwitterKey>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TwitterKey) {
        this.eventManager.broadcast({ name: 'twitterKeyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-twitter-key-popup',
    template: ''
})
export class TwitterKeyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private twitterKeyPopupService: TwitterKeyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.twitterKeyPopupService
                    .open(TwitterKeyDialogComponent as Component, params['id']);
            } else {
                this.twitterKeyPopupService
                    .open(TwitterKeyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
