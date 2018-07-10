import { Moment } from 'moment';
import { ITaskOffer } from 'app/shared/model//task-offer.model';

export const enum OfferState {
  PENDING = 'PENDING',
  COMPLETED = 'COMPLETED',
  SUBMITTED = 'SUBMITTED',
  APPROVED = 'APPROVED',
  REJECTED = 'REJECTED'
}

export interface IServiceOffer {
  id?: number;
  prize?: number;
  state?: OfferState;
  period?: Moment;
  serviceRequestId?: number;
  chosenoffers?: ITaskOffer[];
  amsaUserFirstName?: string;
  amsaUserId?: number;
}

export class ServiceOffer implements IServiceOffer {
  constructor(
    public id?: number,
    public prize?: number,
    public state?: OfferState,
    public period?: Moment,
    public serviceRequestId?: number,
    public chosenoffers?: ITaskOffer[],
    public amsaUserFirstName?: string,
    public amsaUserId?: number
  ) {}
}
