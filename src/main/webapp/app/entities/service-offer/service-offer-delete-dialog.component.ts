import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceOffer } from 'app/shared/model/service-offer.model';
import { ServiceOfferService } from './service-offer.service';

@Component({
  selector: 'jhi-service-offer-delete-dialog',
  templateUrl: './service-offer-delete-dialog.component.html'
})
export class ServiceOfferDeleteDialogComponent {
  serviceOffer: IServiceOffer;

  constructor(
    private serviceOfferService: ServiceOfferService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceOfferService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceOfferListModification',
        content: 'Deleted an serviceOffer'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-offer-delete-popup',
  template: ''
})
export class ServiceOfferDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceOffer }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceOfferDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceOffer = serviceOffer;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
