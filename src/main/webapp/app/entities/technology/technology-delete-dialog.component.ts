import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITechnology } from 'app/shared/model/technology.model';
import { TechnologyService } from './technology.service';

@Component({
  selector: 'jhi-technology-delete-dialog',
  templateUrl: './technology-delete-dialog.component.html'
})
export class TechnologyDeleteDialogComponent {
  technology: ITechnology;

  constructor(private technologyService: TechnologyService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.technologyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'technologyListModification',
        content: 'Deleted an technology'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-technology-delete-popup',
  template: ''
})
export class TechnologyDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ technology }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TechnologyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.technology = technology;
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
