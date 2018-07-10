import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAMSAUser } from 'app/shared/model/amsa-user.model';
import { AMSAUserService } from './amsa-user.service';

@Component({
  selector: 'jhi-amsa-user-delete-dialog',
  templateUrl: './amsa-user-delete-dialog.component.html'
})
export class AMSAUserDeleteDialogComponent {
  aMSAUser: IAMSAUser;

  constructor(private aMSAUserService: AMSAUserService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.aMSAUserService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'aMSAUserListModification',
        content: 'Deleted an aMSAUser'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-amsa-user-delete-popup',
  template: ''
})
export class AMSAUserDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aMSAUser }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AMSAUserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.aMSAUser = aMSAUser;
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
