import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskOrder } from 'app/shared/model/task-order.model';
import { TaskOrderService } from './task-order.service';

@Component({
  selector: 'jhi-task-order-delete-dialog',
  templateUrl: './task-order-delete-dialog.component.html'
})
export class TaskOrderDeleteDialogComponent {
  taskOrder: ITaskOrder;

  constructor(private taskOrderService: TaskOrderService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.taskOrderService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'taskOrderListModification',
        content: 'Deleted an taskOrder'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-task-order-delete-popup',
  template: ''
})
export class TaskOrderDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ taskOrder }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TaskOrderDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.taskOrder = taskOrder;
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
