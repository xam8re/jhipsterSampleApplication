import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TwitterKey } from './twitter-key.model';
import { TwitterKeyPopupService } from './twitter-key-popup.service';
import { TwitterKeyService } from './twitter-key.service';

@Component({
    selector: 'jhi-twitter-key-delete-dialog',
    templateUrl: './twitter-key-delete-dialog.component.html'
})
export class TwitterKeyDeleteDialogComponent {

    twitterKey: TwitterKey;

    constructor(
        private twitterKeyService: TwitterKeyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.twitterKeyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'twitterKeyListModification',
                content: 'Deleted an twitterKey'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-twitter-key-delete-popup',
    template: ''
})
export class TwitterKeyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private twitterKeyPopupService: TwitterKeyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.twitterKeyPopupService
                .open(TwitterKeyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
