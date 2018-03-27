import { BaseEntity } from './../../shared';

export class JobData implements BaseEntity {
    constructor(
        public id?: number,
        public nRetweet?: number,
        public nLike?: number,
        public nFollower?: number,
        public nTweetUser?: number,
        public tweet?: BaseEntity,
    ) {
    }
}
