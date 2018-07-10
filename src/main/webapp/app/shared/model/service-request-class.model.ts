export interface IServiceRequestClass {
  id?: number;
  name?: string;
  showOriginalCAD?: boolean;
  showDesignSpaceCAD?: boolean;
  showNonDesignSpaceCAD?: boolean;
  showTechSpec?: boolean;
  showMaterial?: boolean;
  showDimensions?: boolean;
  showPhoto?: boolean;
}

export class ServiceRequestClass implements IServiceRequestClass {
  constructor(
    public id?: number,
    public name?: string,
    public showOriginalCAD?: boolean,
    public showDesignSpaceCAD?: boolean,
    public showNonDesignSpaceCAD?: boolean,
    public showTechSpec?: boolean,
    public showMaterial?: boolean,
    public showDimensions?: boolean,
    public showPhoto?: boolean
  ) {
    this.showOriginalCAD = false;
    this.showDesignSpaceCAD = false;
    this.showNonDesignSpaceCAD = false;
    this.showTechSpec = false;
    this.showMaterial = false;
    this.showDimensions = false;
    this.showPhoto = false;
  }
}
