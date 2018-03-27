import { BaseEntity } from './../../shared';

export class TweetTemplate implements BaseEntity {
    constructor(
        public id?: number,
        public pattern?: string,
        public descrizione?: string,
        public twitterKey?: BaseEntity,
    ) {
    }
}
