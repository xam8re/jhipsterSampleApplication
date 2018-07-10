export interface ITechnologyClass {
  id?: number;
  name?: string;
  showAccuracy?: boolean;
  showPortable?: boolean;
  showMaterial?: boolean;
}

export class TechnologyClass implements ITechnologyClass {
  constructor(
    public id?: number,
    public name?: string,
    public showAccuracy?: boolean,
    public showPortable?: boolean,
    public showMaterial?: boolean
  ) {
    this.showAccuracy = false;
    this.showPortable = false;
    this.showMaterial = false;
  }
}
