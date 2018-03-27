import { BaseEntity } from './../../shared';

export class TweetCategory implements BaseEntity {
    constructor(
        public id?: number,
        public categoria?: string,
    ) {
    }
}
