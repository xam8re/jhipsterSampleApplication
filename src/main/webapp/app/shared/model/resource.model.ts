import { IMaterial } from 'app/shared/model//material.model';

export interface IResource {
  id?: number;
  accuracy?: string;
  portable?: boolean;
  materials?: IMaterial[];
  machineModelId?: number;
  workVolumeId?: number;
  ownerId?: number;
}

export class Resource implements IResource {
  constructor(
    public id?: number,
    public accuracy?: string,
    public portable?: boolean,
    public materials?: IMaterial[],
    public machineModelId?: number,
    public workVolumeId?: number,
    public ownerId?: number
  ) {
    this.portable = false;
  }
}
