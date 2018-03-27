import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TweetCategory } from './tweet-category.model';
import { TweetCategoryService } from './tweet-category.service';

@Component({
    selector: 'jhi-tweet-category-detail',
    templateUrl: './tweet-category-detail.component.html'
})
export class TweetCategoryDetailComponent implements OnInit, OnDestroy {

    tweetCategory: TweetCategory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tweetCategoryService: TweetCategoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTweetCategories();
    }

    load(id) {
        this.tweetCategoryService.find(id)
            .subscribe((tweetCategoryResponse: HttpResponse<TweetCategory>) => {
                this.tweetCategory = tweetCategoryResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTweetCategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tweetCategoryListModification',
            (response) => this.load(this.tweetCategory.id)
        );
    }
}
