export interface ITechnology {
  id?: number;
  name?: string;
  technologyClassId?: number;
  machineModelId?: number;
}

export class Technology implements ITechnology {
  constructor(public id?: number, public name?: string, public technologyClassId?: number, public machineModelId?: number) {}
}
