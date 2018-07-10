import { Moment } from 'moment';
import { ITaskOffer } from 'app/shared/model//task-offer.model';

export interface ITaskRequest {
  id?: number;
  title?: string;
  description?: string;
  period?: Moment;
  serviceRequestId?: number;
  technologyId?: number;
  materialId?: number;
  volumeId?: number;
  offers?: ITaskOffer[];
}

export class TaskRequest implements ITaskRequest {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public period?: Moment,
    public serviceRequestId?: number,
    public technologyId?: number,
    public materialId?: number,
    public volumeId?: number,
    public offers?: ITaskOffer[]
  ) {}
}
