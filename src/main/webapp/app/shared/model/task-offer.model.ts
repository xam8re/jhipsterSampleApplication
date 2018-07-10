import { Moment } from 'moment';

export const enum OfferState {
  PENDING = 'PENDING',
  COMPLETED = 'COMPLETED',
  SUBMITTED = 'SUBMITTED',
  APPROVED = 'APPROVED',
  REJECTED = 'REJECTED'
}

export interface ITaskOffer {
  id?: number;
  prize?: number;
  state?: OfferState;
  period?: Moment;
  taskRequestId?: number;
  supplierId?: number;
  serviceOfferId?: number;
}

export class TaskOffer implements ITaskOffer {
  constructor(
    public id?: number,
    public prize?: number,
    public state?: OfferState,
    public period?: Moment,
    public taskRequestId?: number,
    public supplierId?: number,
    public serviceOfferId?: number
  ) {}
}
