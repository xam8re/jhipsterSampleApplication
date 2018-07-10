import { Moment } from 'moment';

export const enum OrderState {
  PENDING = 'PENDING',
  WIP = 'WIP',
  INTRANSIT = 'INTRANSIT',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED'
}

export interface IOrderHistory {
  id?: number;
  state?: OrderState;
  date?: Moment;
  byId?: number;
  serviceOrderId?: number;
  taskOrderId?: number;
}

export class OrderHistory implements IOrderHistory {
  constructor(
    public id?: number,
    public state?: OrderState,
    public date?: Moment,
    public byId?: number,
    public serviceOrderId?: number,
    public taskOrderId?: number
  ) {}
}
