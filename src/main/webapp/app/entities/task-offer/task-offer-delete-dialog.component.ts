import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskOffer } from 'app/shared/model/task-offer.model';
import { TaskOfferService } from './task-offer.service';

@Component({
  selector: 'jhi-task-offer-delete-dialog',
  templateUrl: './task-offer-delete-dialog.component.html'
})
export class TaskOfferDeleteDialogComponent {
  taskOffer: ITaskOffer;

  constructor(private taskOfferService: TaskOfferService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.taskOfferService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'taskOfferListModification',
        content: 'Deleted an taskOffer'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-task-offer-delete-popup',
  template: ''
})
export class TaskOfferDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ taskOffer }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TaskOfferDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.taskOffer = taskOffer;
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
