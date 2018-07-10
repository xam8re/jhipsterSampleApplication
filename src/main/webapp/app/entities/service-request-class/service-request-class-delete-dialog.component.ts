import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceRequestClass } from 'app/shared/model/service-request-class.model';
import { ServiceRequestClassService } from './service-request-class.service';

@Component({
  selector: 'jhi-service-request-class-delete-dialog',
  templateUrl: './service-request-class-delete-dialog.component.html'
})
export class ServiceRequestClassDeleteDialogComponent {
  serviceRequestClass: IServiceRequestClass;

  constructor(
    private serviceRequestClassService: ServiceRequestClassService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceRequestClassService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceRequestClassListModification',
        content: 'Deleted an serviceRequestClass'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-request-class-delete-popup',
  template: ''
})
export class ServiceRequestClassDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceRequestClass }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceRequestClassDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.serviceRequestClass = serviceRequestClass;
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
