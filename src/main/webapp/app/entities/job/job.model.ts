import { BaseEntity } from './../../shared';

export const enum RunStatus {
    'IDLE',
    'RUNNING'
}

export class Job implements BaseEntity {
    constructor(
        public id?: number,
        public runData?: any,
        public status?: RunStatus,
    ) {
    }
}
