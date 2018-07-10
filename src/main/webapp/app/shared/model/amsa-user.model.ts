export const enum AMSAuserType {
  CUSTOMER = 'CUSTOMER',
  ADMIN = 'ADMIN',
  SUPPLIER = 'SUPPLIER',
  REGISTERED = 'REGISTERED'
}

export interface IAMSAUser {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  company?: string;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
  country?: string;
  userType?: AMSAuserType;
  jhuserId?: number;
}

export class AMSAUser implements IAMSAUser {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: string,
    public company?: string,
    public streetAddress?: string,
    public postalCode?: string,
    public city?: string,
    public stateProvince?: string,
    public country?: string,
    public userType?: AMSAuserType,
    public jhuserId?: number
  ) {}
}
