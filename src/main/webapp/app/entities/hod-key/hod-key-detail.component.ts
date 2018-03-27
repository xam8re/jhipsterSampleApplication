import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { HodKey } from './hod-key.model';
import { HodKeyService } from './hod-key.service';

@Component({
    selector: 'jhi-hod-key-detail',
    templateUrl: './hod-key-detail.component.html'
})
export class HodKeyDetailComponent implements OnInit, OnDestroy {

    hodKey: HodKey;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hodKeyService: HodKeyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHodKeys();
    }

    load(id) {
        this.hodKeyService.find(id)
            .subscribe((hodKeyResponse: HttpResponse<HodKey>) => {
                this.hodKey = hodKeyResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHodKeys() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hodKeyListModification',
            (response) => this.load(this.hodKey.id)
        );
    }
}
