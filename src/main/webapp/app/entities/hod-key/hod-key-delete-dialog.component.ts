import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HodKey } from './hod-key.model';
import { HodKeyPopupService } from './hod-key-popup.service';
import { HodKeyService } from './hod-key.service';

@Component({
    selector: 'jhi-hod-key-delete-dialog',
    templateUrl: './hod-key-delete-dialog.component.html'
})
export class HodKeyDeleteDialogComponent {

    hodKey: HodKey;

    constructor(
        private hodKeyService: HodKeyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hodKeyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hodKeyListModification',
                content: 'Deleted an hodKey'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hod-key-delete-popup',
    template: ''
})
export class HodKeyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hodKeyPopupService: HodKeyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.hodKeyPopupService
                .open(HodKeyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
