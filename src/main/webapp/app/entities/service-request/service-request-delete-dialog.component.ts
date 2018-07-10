import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceRequest } from 'app/shared/model/service-request.model';
import { ServiceRequestService } from './service-request.service';

@Component({
  selector: 'jhi-service-request-delete-dialog',
  templateUrl: './service-request-delete-dialog.component.html'
})
export class ServiceRequestDeleteDialogComponent {
  serviceRequest: IServiceRequest;

  constructor(
    private serviceRequestService: ServiceRequestService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceRequestService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceRequestListModification',
        content: 'Deleted an serviceRequest'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-request-delete-popup',
  template: ''
})
export class ServiceRequestDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceRequest }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceRequestDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceRequest = serviceRequest;
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
