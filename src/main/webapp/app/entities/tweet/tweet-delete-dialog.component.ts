import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Tweet } from './tweet.model';
import { TweetPopupService } from './tweet-popup.service';
import { TweetService } from './tweet.service';

@Component({
    selector: 'jhi-tweet-delete-dialog',
    templateUrl: './tweet-delete-dialog.component.html'
})
export class TweetDeleteDialogComponent {

    tweet: Tweet;

    constructor(
        private tweetService: TweetService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tweetService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tweetListModification',
                content: 'Deleted an tweet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tweet-delete-popup',
    template: ''
})
export class TweetDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tweetPopupService: TweetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tweetPopupService
                .open(TweetDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
