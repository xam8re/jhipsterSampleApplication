import { Moment } from 'moment';
import { ITaskRequest } from 'app/shared/model//task-request.model';
import { IServiceOffer } from 'app/shared/model//service-offer.model';
import { IDocument } from 'app/shared/model//document.model';

export interface IServiceRequest {
  id?: number;
  name?: string;
  period?: Moment;
  tasks?: ITaskRequest[];
  offers?: IServiceOffer[];
  materialId?: number;
  volumeId?: number;
  srvclassId?: number;
  amsaUserFirstName?: string;
  amsaUserId?: number;
  documents?: IDocument[];
}

export class ServiceRequest implements IServiceRequest {
  constructor(
    public id?: number,
    public name?: string,
    public period?: Moment,
    public tasks?: ITaskRequest[],
    public offers?: IServiceOffer[],
    public materialId?: number,
    public volumeId?: number,
    public srvclassId?: number,
    public amsaUserFirstName?: string,
    public amsaUserId?: number,
    public documents?: IDocument[]
  ) {}
}
