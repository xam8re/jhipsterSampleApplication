import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Job } from './job.model';
import { JobPopupService } from './job-popup.service';
import { JobService } from './job.service';

@Component({
    selector: 'jhi-job-dialog',
    templateUrl: './job-dialog.component.html'
})
export class JobDialogComponent implements OnInit {

    job: Job;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jobService: JobService,
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
        if (this.job.id !== undefined) {
            this.subscribeToSaveResponse(
                this.jobService.update(this.job));
        } else {
            this.subscribeToSaveResponse(
                this.jobService.create(this.job));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Job>>) {
        result.subscribe((res: HttpResponse<Job>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Job) {
        this.eventManager.broadcast({ name: 'jobListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-job-popup',
    template: ''
})
export class JobPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobPopupService: JobPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.jobPopupService
                    .open(JobDialogComponent as Component, params['id']);
            } else {
                this.jobPopupService
                    .open(JobDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
