import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TweetTemplate } from './tweet-template.model';
import { TweetTemplatePopupService } from './tweet-template-popup.service';
import { TweetTemplateService } from './tweet-template.service';

@Component({
    selector: 'jhi-tweet-template-delete-dialog',
    templateUrl: './tweet-template-delete-dialog.component.html'
})
export class TweetTemplateDeleteDialogComponent {

    tweetTemplate: TweetTemplate;

    constructor(
        private tweetTemplateService: TweetTemplateService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tweetTemplateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tweetTemplateListModification',
                content: 'Deleted an tweetTemplate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tweet-template-delete-popup',
    template: ''
})
export class TweetTemplateDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tweetTemplatePopupService: TweetTemplatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tweetTemplatePopupService
                .open(TweetTemplateDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
