import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAMSAUser } from 'app/shared/model/amsa-user.model';
import { AMSAUserService } from './amsa-user.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-amsa-user-update',
  templateUrl: './amsa-user-update.component.html'
})
export class AMSAUserUpdateComponent implements OnInit {
  private _aMSAUser: IAMSAUser;
  isSaving: boolean;

  users: IUser[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private aMSAUserService: AMSAUserService,
    private userService: UserService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ aMSAUser }) => {
      this.aMSAUser = aMSAUser;
    });
    this.userService.query().subscribe(
      (res: HttpResponse<IUser[]>) => {
        this.users = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.aMSAUser.id !== undefined) {
      this.subscribeToSaveResponse(this.aMSAUserService.update(this.aMSAUser));
    } else {
      this.subscribeToSaveResponse(this.aMSAUserService.create(this.aMSAUser));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IAMSAUser>>) {
    result.subscribe((res: HttpResponse<IAMSAUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
  get aMSAUser() {
    return this._aMSAUser;
  }

  set aMSAUser(aMSAUser: IAMSAUser) {
    this._aMSAUser = aMSAUser;
  }
}
