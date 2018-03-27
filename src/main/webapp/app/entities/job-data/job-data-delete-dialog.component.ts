import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { JobData } from './job-data.model';
import { JobDataPopupService } from './job-data-popup.service';
import { JobDataService } from './job-data.service';

@Component({
    selector: 'jhi-job-data-delete-dialog',
    templateUrl: './job-data-delete-dialog.component.html'
})
export class JobDataDeleteDialogComponent {

    jobData: JobData;

    constructor(
        private jobDataService: JobDataService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jobDataService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'jobDataListModification',
                content: 'Deleted an jobData'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-job-data-delete-popup',
    template: ''
})
export class JobDataDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobDataPopupService: JobDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.jobDataPopupService
                .open(JobDataDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
