export interface IDocument {
  id?: number;
  path?: string;
  type?: string;
  ext?: string;
}

export class Document implements IDocument {
  constructor(public id?: number, public path?: string, public type?: string, public ext?: string) {}
}
