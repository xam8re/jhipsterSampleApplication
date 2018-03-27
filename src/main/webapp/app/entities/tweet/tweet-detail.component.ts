import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Tweet } from './tweet.model';
import { TweetService } from './tweet.service';

@Component({
    selector: 'jhi-tweet-detail',
    templateUrl: './tweet-detail.component.html'
})
export class TweetDetailComponent implements OnInit, OnDestroy {

    tweet: Tweet;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tweetService: TweetService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTweets();
    }

    load(id) {
        this.tweetService.find(id)
            .subscribe((tweetResponse: HttpResponse<Tweet>) => {
                this.tweet = tweetResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTweets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tweetListModification',
            (response) => this.load(this.tweet.id)
        );
    }
}
