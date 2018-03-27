import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TweetTemplate } from './tweet-template.model';
import { TweetTemplateService } from './tweet-template.service';

@Injectable()
export class TweetTemplatePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private tweetTemplateService: TweetTemplateService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.tweetTemplateService.find(id)
                    .subscribe((tweetTemplateResponse: HttpResponse<TweetTemplate>) => {
                        const tweetTemplate: TweetTemplate = tweetTemplateResponse.body;
                        this.ngbModalRef = this.tweetTemplateModalRef(component, tweetTemplate);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.tweetTemplateModalRef(component, new TweetTemplate());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    tweetTemplateModalRef(component: Component, tweetTemplate: TweetTemplate): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tweetTemplate = tweetTemplate;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
