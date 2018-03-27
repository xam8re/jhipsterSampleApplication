import { BaseEntity } from './../../shared';

export class TwitterKey implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public consumerKey?: string,
        public consumerSecret?: string,
        public accessToken?: string,
        public accessTokenSecret?: string,
        public active?: boolean,
    ) {
        this.active = false;
    }
}
