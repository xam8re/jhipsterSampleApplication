import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TweetTemplate } from './tweet-template.model';
import { TweetTemplateService } from './tweet-template.service';

@Component({
    selector: 'jhi-tweet-template-detail',
    templateUrl: './tweet-template-detail.component.html'
})
export class TweetTemplateDetailComponent implements OnInit, OnDestroy {

    tweetTemplate: TweetTemplate;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tweetTemplateService: TweetTemplateService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTweetTemplates();
    }

    load(id) {
        this.tweetTemplateService.find(id)
            .subscribe((tweetTemplateResponse: HttpResponse<TweetTemplate>) => {
                this.tweetTemplate = tweetTemplateResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTweetTemplates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tweetTemplateListModification',
            (response) => this.load(this.tweetTemplate.id)
        );
    }
}
