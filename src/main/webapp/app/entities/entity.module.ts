import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationTweetModule } from './tweet/tweet.module';
import { JhipsterSampleApplicationTweetCategoryModule } from './tweet-category/tweet-category.module';
import { JhipsterSampleApplicationJobModule } from './job/job.module';
import { JhipsterSampleApplicationJobDataModule } from './job-data/job-data.module';
import { JhipsterSampleApplicationTweetTemplateModule } from './tweet-template/tweet-template.module';
import { JhipsterSampleApplicationTwitterKeyModule } from './twitter-key/twitter-key.module';
import { JhipsterSampleApplicationHodKeyModule } from './hod-key/hod-key.module';
import { JhipsterSampleApplicationAMSAUserModule } from './amsa-user/amsa-user.module';
import { JhipsterSampleApplicationDocumentModule } from './document/document.module';
import { JhipsterSampleApplicationMaterialModule } from './material/material.module';
import { JhipsterSampleApplicationDimensionModule } from './dimension/dimension.module';
import { JhipsterSampleApplicationServiceRequestClassModule } from './service-request-class/service-request-class.module';
import { JhipsterSampleApplicationServiceRequestModule } from './service-request/service-request.module';
import { JhipsterSampleApplicationTaskRequestModule } from './task-request/task-request.module';
import { JhipsterSampleApplicationTaskOfferModule } from './task-offer/task-offer.module';
import { JhipsterSampleApplicationServiceOfferModule } from './service-offer/service-offer.module';
import { JhipsterSampleApplicationTechnologyClassModule } from './technology-class/technology-class.module';
import { JhipsterSampleApplicationTechnologyModule } from './technology/technology.module';
import { JhipsterSampleApplicationManufacturerModule } from './manufacturer/manufacturer.module';
import { JhipsterSampleApplicationResourceModule } from './resource/resource.module';
import { JhipsterSampleApplicationMachineModelModule } from './machine-model/machine-model.module';
import { JhipsterSampleApplicationOrderHistoryModule } from './order-history/order-history.module';
import { JhipsterSampleApplicationServiceOrderModule } from './service-order/service-order.module';
import { JhipsterSampleApplicationTaskOrderModule } from './task-order/task-order.module';
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
    JhipsterSampleApplicationAMSAUserModule,
    JhipsterSampleApplicationDocumentModule,
    JhipsterSampleApplicationMaterialModule,
    JhipsterSampleApplicationDimensionModule,
    JhipsterSampleApplicationServiceRequestClassModule,
    JhipsterSampleApplicationServiceRequestModule,
    JhipsterSampleApplicationTaskRequestModule,
    JhipsterSampleApplicationTaskOfferModule,
    JhipsterSampleApplicationServiceOfferModule,
    JhipsterSampleApplicationTechnologyClassModule,
    JhipsterSampleApplicationTechnologyModule,
    JhipsterSampleApplicationManufacturerModule,
    JhipsterSampleApplicationResourceModule,
    JhipsterSampleApplicationMachineModelModule,
    JhipsterSampleApplicationOrderHistoryModule,
    JhipsterSampleApplicationServiceOrderModule,
    JhipsterSampleApplicationTaskOrderModule
    /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
