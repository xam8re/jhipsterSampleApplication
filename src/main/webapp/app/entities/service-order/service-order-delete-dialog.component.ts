import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceOrder } from 'app/shared/model/service-order.model';
import { ServiceOrderService } from './service-order.service';

@Component({
  selector: 'jhi-service-order-delete-dialog',
  templateUrl: './service-order-delete-dialog.component.html'
})
export class ServiceOrderDeleteDialogComponent {
  serviceOrder: IServiceOrder;

  constructor(
    private serviceOrderService: ServiceOrderService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceOrderService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceOrderListModification',
        content: 'Deleted an serviceOrder'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-order-delete-popup',
  template: ''
})
export class ServiceOrderDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceOrder }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceOrderDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceOrder = serviceOrder;
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
