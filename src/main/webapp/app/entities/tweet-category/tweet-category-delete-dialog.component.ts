import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TweetCategory } from './tweet-category.model';
import { TweetCategoryPopupService } from './tweet-category-popup.service';
import { TweetCategoryService } from './tweet-category.service';

@Component({
    selector: 'jhi-tweet-category-delete-dialog',
    templateUrl: './tweet-category-delete-dialog.component.html'
})
export class TweetCategoryDeleteDialogComponent {

    tweetCategory: TweetCategory;

    constructor(
        private tweetCategoryService: TweetCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tweetCategoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tweetCategoryListModification',
                content: 'Deleted an tweetCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tweet-category-delete-popup',
    template: ''
})
export class TweetCategoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tweetCategoryPopupService: TweetCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tweetCategoryPopupService
                .open(TweetCategoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
