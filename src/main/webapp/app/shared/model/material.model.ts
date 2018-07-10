export interface IMaterial {
  id?: number;
  code?: string;
  name?: string;
  description?: string;
  imageId?: number;
  resourceId?: number;
}

export class Material implements IMaterial {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public imageId?: number,
    public resourceId?: number
  ) {}
}
