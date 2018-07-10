export const enum UnitSystem {
  MM = 'MM',
  INCH = 'INCH'
}

export interface IDimension {
  id?: number;
  us?: UnitSystem;
  x?: number;
  y?: number;
  z?: number;
}

export class Dimension implements IDimension {
  constructor(public id?: number, public us?: UnitSystem, public x?: number, public y?: number, public z?: number) {}
}
