import { BaseEntity } from './../../shared';

export const enum Sentiment {
    'POSITIVO',
    'NEGATIVO',
    'NEUTRO'
}

export class Tweet implements BaseEntity {
    constructor(
        public id?: number,
        public tweetId?: string,
        public tweetData?: any,
        public tweetGeo?: string,
        public content?: string,
        public userid?: string,
        public sentiment?: Sentiment,
        public categoria?: BaseEntity,
        public tenplate?: BaseEntity,
        public job?: BaseEntity,
    ) {
    }
}
