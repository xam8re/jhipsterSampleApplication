import { ITechnology } from 'app/shared/model//technology.model';

export interface IMachineModel {
  id?: number;
  name?: string;
  technologies?: ITechnology[];
  manufacturerId?: number;
}

export class MachineModel implements IMachineModel {
  constructor(public id?: number, public name?: string, public technologies?: ITechnology[], public manufacturerId?: number) {}
}
