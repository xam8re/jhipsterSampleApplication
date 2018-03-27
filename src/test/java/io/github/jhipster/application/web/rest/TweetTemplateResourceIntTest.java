package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.TweetTemplate;
import io.github.jhipster.application.repository.TweetTemplateRepository;
import io.github.jhipster.application.service.TweetTemplateService;
import io.github.jhipster.application.repository.search.TweetTemplateSearchRepository;
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
 * Test class for the TweetTemplateResource REST controller.
 *
 * @see TweetTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TweetTemplateResourceIntTest {

    private static final String DEFAULT_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_PATTERN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    @Autowired
    private TweetTemplateRepository tweetTemplateRepository;

    @Autowired
    private TweetTemplateService tweetTemplateService;

    @Autowired
    private TweetTemplateSearchRepository tweetTemplateSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTweetTemplateMockMvc;

    private TweetTemplate tweetTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TweetTemplateResource tweetTemplateResource = new TweetTemplateResource(tweetTemplateService);
        this.restTweetTemplateMockMvc = MockMvcBuilders.standaloneSetup(tweetTemplateResource)
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
    public static TweetTemplate createEntity(EntityManager em) {
        TweetTemplate tweetTemplate = new TweetTemplate()
            .pattern(DEFAULT_PATTERN)
            .descrizione(DEFAULT_DESCRIZIONE);
        return tweetTemplate;
    }

    @Before
    public void initTest() {
        tweetTemplateSearchRepository.deleteAll();
        tweetTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createTweetTemplate() throws Exception {
        int databaseSizeBeforeCreate = tweetTemplateRepository.findAll().size();

        // Create the TweetTemplate
        restTweetTemplateMockMvc.perform(post("/api/tweet-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweetTemplate)))
            .andExpect(status().isCreated());

        // Validate the TweetTemplate in the database
        List<TweetTemplate> tweetTemplateList = tweetTemplateRepository.findAll();
        assertThat(tweetTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        TweetTemplate testTweetTemplate = tweetTemplateList.get(tweetTemplateList.size() - 1);
        assertThat(testTweetTemplate.getPattern()).isEqualTo(DEFAULT_PATTERN);
        assertThat(testTweetTemplate.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);

        // Validate the TweetTemplate in Elasticsearch
        TweetTemplate tweetTemplateEs = tweetTemplateSearchRepository.findOne(testTweetTemplate.getId());
        assertThat(tweetTemplateEs).isEqualToIgnoringGivenFields(testTweetTemplate);
    }

    @Test
    @Transactional
    public void createTweetTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tweetTemplateRepository.findAll().size();

        // Create the TweetTemplate with an existing ID
        tweetTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTweetTemplateMockMvc.perform(post("/api/tweet-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweetTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the TweetTemplate in the database
        List<TweetTemplate> tweetTemplateList = tweetTemplateRepository.findAll();
        assertThat(tweetTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPatternIsRequired() throws Exception {
        int databaseSizeBeforeTest = tweetTemplateRepository.findAll().size();
        // set the field null
        tweetTemplate.setPattern(null);

        // Create the TweetTemplate, which fails.

        restTweetTemplateMockMvc.perform(post("/api/tweet-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweetTemplate)))
            .andExpect(status().isBadRequest());

        List<TweetTemplate> tweetTemplateList = tweetTemplateRepository.findAll();
        assertThat(tweetTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTweetTemplates() throws Exception {
        // Initialize the database
        tweetTemplateRepository.saveAndFlush(tweetTemplate);

        // Get all the tweetTemplateList
        restTweetTemplateMockMvc.perform(get("/api/tweet-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tweetTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].pattern").value(hasItem(DEFAULT_PATTERN.toString())))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE.toString())));
    }

    @Test
    @Transactional
    public void getTweetTemplate() throws Exception {
        // Initialize the database
        tweetTemplateRepository.saveAndFlush(tweetTemplate);

        // Get the tweetTemplate
        restTweetTemplateMockMvc.perform(get("/api/tweet-templates/{id}", tweetTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tweetTemplate.getId().intValue()))
            .andExpect(jsonPath("$.pattern").value(DEFAULT_PATTERN.toString()))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTweetTemplate() throws Exception {
        // Get the tweetTemplate
        restTweetTemplateMockMvc.perform(get("/api/tweet-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTweetTemplate() throws Exception {
        // Initialize the database
        tweetTemplateService.save(tweetTemplate);

        int databaseSizeBeforeUpdate = tweetTemplateRepository.findAll().size();

        // Update the tweetTemplate
        TweetTemplate updatedTweetTemplate = tweetTemplateRepository.findOne(tweetTemplate.getId());
        // Disconnect from session so that the updates on updatedTweetTemplate are not directly saved in db
        em.detach(updatedTweetTemplate);
        updatedTweetTemplate
            .pattern(UPDATED_PATTERN)
            .descrizione(UPDATED_DESCRIZIONE);

        restTweetTemplateMockMvc.perform(put("/api/tweet-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTweetTemplate)))
            .andExpect(status().isOk());

        // Validate the TweetTemplate in the database
        List<TweetTemplate> tweetTemplateList = tweetTemplateRepository.findAll();
        assertThat(tweetTemplateList).hasSize(databaseSizeBeforeUpdate);
        TweetTemplate testTweetTemplate = tweetTemplateList.get(tweetTemplateList.size() - 1);
        assertThat(testTweetTemplate.getPattern()).isEqualTo(UPDATED_PATTERN);
        assertThat(testTweetTemplate.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);

        // Validate the TweetTemplate in Elasticsearch
        TweetTemplate tweetTemplateEs = tweetTemplateSearchRepository.findOne(testTweetTemplate.getId());
        assertThat(tweetTemplateEs).isEqualToIgnoringGivenFields(testTweetTemplate);
    }

    @Test
    @Transactional
    public void updateNonExistingTweetTemplate() throws Exception {
        int databaseSizeBeforeUpdate = tweetTemplateRepository.findAll().size();

        // Create the TweetTemplate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTweetTemplateMockMvc.perform(put("/api/tweet-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweetTemplate)))
            .andExpect(status().isCreated());

        // Validate the TweetTemplate in the database
        List<TweetTemplate> tweetTemplateList = tweetTemplateRepository.findAll();
        assertThat(tweetTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTweetTemplate() throws Exception {
        // Initialize the database
        tweetTemplateService.save(tweetTemplate);

        int databaseSizeBeforeDelete = tweetTemplateRepository.findAll().size();

        // Get the tweetTemplate
        restTweetTemplateMockMvc.perform(delete("/api/tweet-templates/{id}", tweetTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tweetTemplateExistsInEs = tweetTemplateSearchRepository.exists(tweetTemplate.getId());
        assertThat(tweetTemplateExistsInEs).isFalse();

        // Validate the database is empty
        List<TweetTemplate> tweetTemplateList = tweetTemplateRepository.findAll();
        assertThat(tweetTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTweetTemplate() throws Exception {
        // Initialize the database
        tweetTemplateService.save(tweetTemplate);

        // Search the tweetTemplate
        restTweetTemplateMockMvc.perform(get("/api/_search/tweet-templates?query=id:" + tweetTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tweetTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].pattern").value(hasItem(DEFAULT_PATTERN.toString())))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TweetTemplate.class);
        TweetTemplate tweetTemplate1 = new TweetTemplate();
        tweetTemplate1.setId(1L);
        TweetTemplate tweetTemplate2 = new TweetTemplate();
        tweetTemplate2.setId(tweetTemplate1.getId());
        assertThat(tweetTemplate1).isEqualTo(tweetTemplate2);
        tweetTemplate2.setId(2L);
        assertThat(tweetTemplate1).isNotEqualTo(tweetTemplate2);
        tweetTemplate1.setId(null);
        assertThat(tweetTemplate1).isNotEqualTo(tweetTemplate2);
    }
}
