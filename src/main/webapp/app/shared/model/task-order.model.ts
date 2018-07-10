import { IOrderHistory } from 'app/shared/model//order-history.model';

export const enum OrderState {
  PENDING = 'PENDING',
  WIP = 'WIP',
  INTRANSIT = 'INTRANSIT',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED'
}

export interface ITaskOrder {
  id?: number;
  state?: OrderState;
  orderHistories?: IOrderHistory[];
}

export class TaskOrder implements ITaskOrder {
  constructor(public id?: number, public state?: OrderState, public orderHistories?: IOrderHistory[]) {}
}
