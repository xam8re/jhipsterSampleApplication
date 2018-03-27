import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HodKey } from './hod-key.model';
import { HodKeyPopupService } from './hod-key-popup.service';
import { HodKeyService } from './hod-key.service';

@Component({
    selector: 'jhi-hod-key-dialog',
    templateUrl: './hod-key-dialog.component.html'
})
export class HodKeyDialogComponent implements OnInit {

    hodKey: HodKey;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private hodKeyService: HodKeyService,
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
        if (this.hodKey.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hodKeyService.update(this.hodKey));
        } else {
            this.subscribeToSaveResponse(
                this.hodKeyService.create(this.hodKey));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<HodKey>>) {
        result.subscribe((res: HttpResponse<HodKey>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HodKey) {
        this.eventManager.broadcast({ name: 'hodKeyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-hod-key-popup',
    template: ''
})
export class HodKeyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hodKeyPopupService: HodKeyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hodKeyPopupService
                    .open(HodKeyDialogComponent as Component, params['id']);
            } else {
                this.hodKeyPopupService
                    .open(HodKeyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
