import { BaseEntity } from './../../shared';

export class HodKey implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public hodkey?: string,
    ) {
    }
}
