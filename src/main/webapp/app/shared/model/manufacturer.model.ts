export interface IManufacturer {
  id?: number;
  name?: string;
}

export class Manufacturer implements IManufacturer {
  constructor(public id?: number, public name?: string) {}
}
