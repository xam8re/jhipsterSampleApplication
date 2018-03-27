import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationTweetModule } from './tweet/tweet.module';
import { JhipsterSampleApplicationTweetCategoryModule } from './tweet-category/tweet-category.module';
import { JhipsterSampleApplicationJobModule } from './job/job.module';
import { JhipsterSampleApplicationJobDataModule } from './job-data/job-data.module';
import { JhipsterSampleApplicationTweetTemplateModule } from './tweet-template/tweet-template.module';
import { JhipsterSampleApplicationTwitterKeyModule } from './twitter-key/twitter-key.module';
import { JhipsterSampleApplicationHodKeyModule } from './hod-key/hod-key.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterSampleApplicationTweetModule,
        JhipsterSampleApplicationTweetCategoryModule,
        JhipsterSampleApplicationJobModule,
        JhipsterSampleApplicationJobDataModule,
        JhipsterSampleApplicationTweetTemplateModule,
        JhipsterSampleApplicationTwitterKeyModule,
        JhipsterSampleApplicationHodKeyModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
