import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TwitterKey } from './twitter-key.model';
import { TwitterKeyService } from './twitter-key.service';

@Component({
    selector: 'jhi-twitter-key-detail',
    templateUrl: './twitter-key-detail.component.html'
})
export class TwitterKeyDetailComponent implements OnInit, OnDestroy {

    twitterKey: TwitterKey;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private twitterKeyService: TwitterKeyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTwitterKeys();
    }

    load(id) {
        this.twitterKeyService.find(id)
            .subscribe((twitterKeyResponse: HttpResponse<TwitterKey>) => {
                this.twitterKey = twitterKeyResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTwitterKeys() {
        this.eventSubscriber = this.eventManager.subscribe(
            'twitterKeyListModification',
            (response) => this.load(this.twitterKey.id)
        );
    }
}
