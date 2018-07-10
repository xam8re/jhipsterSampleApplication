import { IOrderHistory } from 'app/shared/model//order-history.model';

export const enum OrderState {
  PENDING = 'PENDING',
  WIP = 'WIP',
  INTRANSIT = 'INTRANSIT',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED'
}

export interface IServiceOrder {
  id?: number;
  state?: OrderState;
  orderHistories?: IOrderHistory[];
}

export class ServiceOrder implements IServiceOrder {
  constructor(public id?: number, public state?: OrderState, public orderHistories?: IOrderHistory[]) {}
}
