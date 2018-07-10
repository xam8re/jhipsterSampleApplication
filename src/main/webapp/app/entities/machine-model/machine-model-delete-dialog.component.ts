import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMachineModel } from 'app/shared/model/machine-model.model';
import { MachineModelService } from './machine-model.service';

@Component({
  selector: 'jhi-machine-model-delete-dialog',
  templateUrl: './machine-model-delete-dialog.component.html'
})
export class MachineModelDeleteDialogComponent {
  machineModel: IMachineModel;

  constructor(
    private machineModelService: MachineModelService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.machineModelService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'machineModelListModification',
        content: 'Deleted an machineModel'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-machine-model-delete-popup',
  template: ''
})
export class MachineModelDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ machineModel }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MachineModelDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.machineModel = machineModel;
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
