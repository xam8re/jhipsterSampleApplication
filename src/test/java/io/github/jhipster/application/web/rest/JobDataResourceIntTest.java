package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.JobData;
import io.github.jhipster.application.repository.JobDataRepository;
import io.github.jhipster.application.service.JobDataService;
import io.github.jhipster.application.repository.search.JobDataSearchRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JobDataResource REST controller.
 *
 * @see JobDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class JobDataResourceIntTest {

    private static final Integer DEFAULT_N_RETWEET = 1;
    private static final Integer UPDATED_N_RETWEET = 2;

    private static final Integer DEFAULT_N_LIKE = 1;
    private static final Integer UPDATED_N_LIKE = 2;

    private static final Integer DEFAULT_N_FOLLOWER = 1;
    private static final Integer UPDATED_N_FOLLOWER = 2;

    private static final Integer DEFAULT_N_TWEET_USER = 1;
    private static final Integer UPDATED_N_TWEET_USER = 2;

    @Autowired
    private JobDataRepository jobDataRepository;

    @Autowired
    private JobDataService jobDataService;

    @Autowired
    private JobDataSearchRepository jobDataSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobDataMockMvc;

    private JobData jobData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobDataResource jobDataResource = new JobDataResource(jobDataService);
        this.restJobDataMockMvc = MockMvcBuilders.standaloneSetup(jobDataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobData createEntity(EntityManager em) {
        JobData jobData = new JobData()
            .nRetweet(DEFAULT_N_RETWEET)
            .nLike(DEFAULT_N_LIKE)
            .nFollower(DEFAULT_N_FOLLOWER)
            .nTweetUser(DEFAULT_N_TWEET_USER);
        return jobData;
    }

    @Before
    public void initTest() {
        jobDataSearchRepository.deleteAll();
        jobData = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobData() throws Exception {
        int databaseSizeBeforeCreate = jobDataRepository.findAll().size();

        // Create the JobData
        restJobDataMockMvc.perform(post("/api/job-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobData)))
            .andExpect(status().isCreated());

        // Validate the JobData in the database
        List<JobData> jobDataList = jobDataRepository.findAll();
        assertThat(jobDataList).hasSize(databaseSizeBeforeCreate + 1);
        JobData testJobData = jobDataList.get(jobDataList.size() - 1);
        assertThat(testJobData.getnRetweet()).isEqualTo(DEFAULT_N_RETWEET);
        assertThat(testJobData.getnLike()).isEqualTo(DEFAULT_N_LIKE);
        assertThat(testJobData.getnFollower()).isEqualTo(DEFAULT_N_FOLLOWER);
        assertThat(testJobData.getnTweetUser()).isEqualTo(DEFAULT_N_TWEET_USER);

        // Validate the JobData in Elasticsearch
        JobData jobDataEs = jobDataSearchRepository.findOne(testJobData.getId());
        assertThat(jobDataEs).isEqualToIgnoringGivenFields(testJobData);
    }

    @Test
    @Transactional
    public void createJobDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobDataRepository.findAll().size();

        // Create the JobData with an existing ID
        jobData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobDataMockMvc.perform(post("/api/job-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobData)))
            .andExpect(status().isBadRequest());

        // Validate the JobData in the database
        List<JobData> jobDataList = jobDataRepository.findAll();
        assertThat(jobDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJobData() throws Exception {
        // Initialize the database
        jobDataRepository.saveAndFlush(jobData);

        // Get all the jobDataList
        restJobDataMockMvc.perform(get("/api/job-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobData.getId().intValue())))
            .andExpect(jsonPath("$.[*].nRetweet").value(hasItem(DEFAULT_N_RETWEET)))
            .andExpect(jsonPath("$.[*].nLike").value(hasItem(DEFAULT_N_LIKE)))
            .andExpect(jsonPath("$.[*].nFollower").value(hasItem(DEFAULT_N_FOLLOWER)))
            .andExpect(jsonPath("$.[*].nTweetUser").value(hasItem(DEFAULT_N_TWEET_USER)));
    }

    @Test
    @Transactional
    public void getJobData() throws Exception {
        // Initialize the database
        jobDataRepository.saveAndFlush(jobData);

        // Get the jobData
        restJobDataMockMvc.perform(get("/api/job-data/{id}", jobData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobData.getId().intValue()))
            .andExpect(jsonPath("$.nRetweet").value(DEFAULT_N_RETWEET))
            .andExpect(jsonPath("$.nLike").value(DEFAULT_N_LIKE))
            .andExpect(jsonPath("$.nFollower").value(DEFAULT_N_FOLLOWER))
            .andExpect(jsonPath("$.nTweetUser").value(DEFAULT_N_TWEET_USER));
    }

    @Test
    @Transactional
    public void getNonExistingJobData() throws Exception {
        // Get the jobData
        restJobDataMockMvc.perform(get("/api/job-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobData() throws Exception {
        // Initialize the database
        jobDataService.save(jobData);

        int databaseSizeBeforeUpdate = jobDataRepository.findAll().size();

        // Update the jobData
        JobData updatedJobData = jobDataRepository.findOne(jobData.getId());
        // Disconnect from session so that the updates on updatedJobData are not directly saved in db
        em.detach(updatedJobData);
        updatedJobData
            .nRetweet(UPDATED_N_RETWEET)
            .nLike(UPDATED_N_LIKE)
            .nFollower(UPDATED_N_FOLLOWER)
            .nTweetUser(UPDATED_N_TWEET_USER);

        restJobDataMockMvc.perform(put("/api/job-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobData)))
            .andExpect(status().isOk());

        // Validate the JobData in the database
        List<JobData> jobDataList = jobDataRepository.findAll();
        assertThat(jobDataList).hasSize(databaseSizeBeforeUpdate);
        JobData testJobData = jobDataList.get(jobDataList.size() - 1);
        assertThat(testJobData.getnRetweet()).isEqualTo(UPDATED_N_RETWEET);
        assertThat(testJobData.getnLike()).isEqualTo(UPDATED_N_LIKE);
        assertThat(testJobData.getnFollower()).isEqualTo(UPDATED_N_FOLLOWER);
        assertThat(testJobData.getnTweetUser()).isEqualTo(UPDATED_N_TWEET_USER);

        // Validate the JobData in Elasticsearch
        JobData jobDataEs = jobDataSearchRepository.findOne(testJobData.getId());
        assertThat(jobDataEs).isEqualToIgnoringGivenFields(testJobData);
    }

    @Test
    @Transactional
    public void updateNonExistingJobData() throws Exception {
        int databaseSizeBeforeUpdate = jobDataRepository.findAll().size();

        // Create the JobData

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobDataMockMvc.perform(put("/api/job-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobData)))
            .andExpect(status().isCreated());

        // Validate the JobData in the database
        List<JobData> jobDataList = jobDataRepository.findAll();
        assertThat(jobDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobData() throws Exception {
        // Initialize the database
        jobDataService.save(jobData);

        int databaseSizeBeforeDelete = jobDataRepository.findAll().size();

        // Get the jobData
        restJobDataMockMvc.perform(delete("/api/job-data/{id}", jobData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean jobDataExistsInEs = jobDataSearchRepository.exists(jobData.getId());
        assertThat(jobDataExistsInEs).isFalse();

        // Validate the database is empty
        List<JobData> jobDataList = jobDataRepository.findAll();
        assertThat(jobDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchJobData() throws Exception {
        // Initialize the database
        jobDataService.save(jobData);

        // Search the jobData
        restJobDataMockMvc.perform(get("/api/_search/job-data?query=id:" + jobData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobData.getId().intValue())))
            .andExpect(jsonPath("$.[*].nRetweet").value(hasItem(DEFAULT_N_RETWEET)))
            .andExpect(jsonPath("$.[*].nLike").value(hasItem(DEFAULT_N_LIKE)))
            .andExpect(jsonPath("$.[*].nFollower").value(hasItem(DEFAULT_N_FOLLOWER)))
            .andExpect(jsonPath("$.[*].nTweetUser").value(hasItem(DEFAULT_N_TWEET_USER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobData.class);
        JobData jobData1 = new JobData();
        jobData1.setId(1L);
        JobData jobData2 = new JobData();
        jobData2.setId(jobData1.getId());
        assertThat(jobData1).isEqualTo(jobData2);
        jobData2.setId(2L);
        assertThat(jobData1).isNotEqualTo(jobData2);
        jobData1.setId(null);
        assertThat(jobData1).isNotEqualTo(jobData2);
    }
}
