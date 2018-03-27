package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.TwitterKey;
import io.github.jhipster.application.repository.TwitterKeyRepository;
import io.github.jhipster.application.service.TwitterKeyService;
import io.github.jhipster.application.repository.search.TwitterKeySearchRepository;
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
 * Test class for the TwitterKeyResource REST controller.
 *
 * @see TwitterKeyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TwitterKeyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONSUMER_KEY = "AAAAAAAAAA";
    private static final String UPDATED_CONSUMER_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_CONSUMER_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_CONSUMER_SECRET = "BBBBBBBBBB";

    private static final String DEFAULT_ACCESS_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_ACCESS_TOKEN_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS_TOKEN_SECRET = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private TwitterKeyRepository twitterKeyRepository;

    @Autowired
    private TwitterKeyService twitterKeyService;

    @Autowired
    private TwitterKeySearchRepository twitterKeySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTwitterKeyMockMvc;

    private TwitterKey twitterKey;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TwitterKeyResource twitterKeyResource = new TwitterKeyResource(twitterKeyService);
        this.restTwitterKeyMockMvc = MockMvcBuilders.standaloneSetup(twitterKeyResource)
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
    public static TwitterKey createEntity(EntityManager em) {
        TwitterKey twitterKey = new TwitterKey()
            .name(DEFAULT_NAME)
            .consumerKey(DEFAULT_CONSUMER_KEY)
            .consumerSecret(DEFAULT_CONSUMER_SECRET)
            .accessToken(DEFAULT_ACCESS_TOKEN)
            .accessTokenSecret(DEFAULT_ACCESS_TOKEN_SECRET)
            .active(DEFAULT_ACTIVE);
        return twitterKey;
    }

    @Before
    public void initTest() {
        twitterKeySearchRepository.deleteAll();
        twitterKey = createEntity(em);
    }

    @Test
    @Transactional
    public void createTwitterKey() throws Exception {
        int databaseSizeBeforeCreate = twitterKeyRepository.findAll().size();

        // Create the TwitterKey
        restTwitterKeyMockMvc.perform(post("/api/twitter-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(twitterKey)))
            .andExpect(status().isCreated());

        // Validate the TwitterKey in the database
        List<TwitterKey> twitterKeyList = twitterKeyRepository.findAll();
        assertThat(twitterKeyList).hasSize(databaseSizeBeforeCreate + 1);
        TwitterKey testTwitterKey = twitterKeyList.get(twitterKeyList.size() - 1);
        assertThat(testTwitterKey.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTwitterKey.getConsumerKey()).isEqualTo(DEFAULT_CONSUMER_KEY);
        assertThat(testTwitterKey.getConsumerSecret()).isEqualTo(DEFAULT_CONSUMER_SECRET);
        assertThat(testTwitterKey.getAccessToken()).isEqualTo(DEFAULT_ACCESS_TOKEN);
        assertThat(testTwitterKey.getAccessTokenSecret()).isEqualTo(DEFAULT_ACCESS_TOKEN_SECRET);
        assertThat(testTwitterKey.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the TwitterKey in Elasticsearch
        TwitterKey twitterKeyEs = twitterKeySearchRepository.findOne(testTwitterKey.getId());
        assertThat(twitterKeyEs).isEqualToIgnoringGivenFields(testTwitterKey);
    }

    @Test
    @Transactional
    public void createTwitterKeyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = twitterKeyRepository.findAll().size();

        // Create the TwitterKey with an existing ID
        twitterKey.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTwitterKeyMockMvc.perform(post("/api/twitter-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(twitterKey)))
            .andExpect(status().isBadRequest());

        // Validate the TwitterKey in the database
        List<TwitterKey> twitterKeyList = twitterKeyRepository.findAll();
        assertThat(twitterKeyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = twitterKeyRepository.findAll().size();
        // set the field null
        twitterKey.setName(null);

        // Create the TwitterKey, which fails.

        restTwitterKeyMockMvc.perform(post("/api/twitter-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(twitterKey)))
            .andExpect(status().isBadRequest());

        List<TwitterKey> twitterKeyList = twitterKeyRepository.findAll();
        assertThat(twitterKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConsumerKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = twitterKeyRepository.findAll().size();
        // set the field null
        twitterKey.setConsumerKey(null);

        // Create the TwitterKey, which fails.

        restTwitterKeyMockMvc.perform(post("/api/twitter-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(twitterKey)))
            .andExpect(status().isBadRequest());

        List<TwitterKey> twitterKeyList = twitterKeyRepository.findAll();
        assertThat(twitterKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConsumerSecretIsRequired() throws Exception {
        int databaseSizeBeforeTest = twitterKeyRepository.findAll().size();
        // set the field null
        twitterKey.setConsumerSecret(null);

        // Create the TwitterKey, which fails.

        restTwitterKeyMockMvc.perform(post("/api/twitter-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(twitterKey)))
            .andExpect(status().isBadRequest());

        List<TwitterKey> twitterKeyList = twitterKeyRepository.findAll();
        assertThat(twitterKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccessTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = twitterKeyRepository.findAll().size();
        // set the field null
        twitterKey.setAccessToken(null);

        // Create the TwitterKey, which fails.

        restTwitterKeyMockMvc.perform(post("/api/twitter-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(twitterKey)))
            .andExpect(status().isBadRequest());

        List<TwitterKey> twitterKeyList = twitterKeyRepository.findAll();
        assertThat(twitterKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccessTokenSecretIsRequired() throws Exception {
        int databaseSizeBeforeTest = twitterKeyRepository.findAll().size();
        // set the field null
        twitterKey.setAccessTokenSecret(null);

        // Create the TwitterKey, which fails.

        restTwitterKeyMockMvc.perform(post("/api/twitter-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(twitterKey)))
            .andExpect(status().isBadRequest());

        List<TwitterKey> twitterKeyList = twitterKeyRepository.findAll();
        assertThat(twitterKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = twitterKeyRepository.findAll().size();
        // set the field null
        twitterKey.setActive(null);

        // Create the TwitterKey, which fails.

        restTwitterKeyMockMvc.perform(post("/api/twitter-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(twitterKey)))
            .andExpect(status().isBadRequest());

        List<TwitterKey> twitterKeyList = twitterKeyRepository.findAll();
        assertThat(twitterKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTwitterKeys() throws Exception {
        // Initialize the database
        twitterKeyRepository.saveAndFlush(twitterKey);

        // Get all the twitterKeyList
        restTwitterKeyMockMvc.perform(get("/api/twitter-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(twitterKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].consumerKey").value(hasItem(DEFAULT_CONSUMER_KEY.toString())))
            .andExpect(jsonPath("$.[*].consumerSecret").value(hasItem(DEFAULT_CONSUMER_SECRET.toString())))
            .andExpect(jsonPath("$.[*].accessToken").value(hasItem(DEFAULT_ACCESS_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].accessTokenSecret").value(hasItem(DEFAULT_ACCESS_TOKEN_SECRET.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getTwitterKey() throws Exception {
        // Initialize the database
        twitterKeyRepository.saveAndFlush(twitterKey);

        // Get the twitterKey
        restTwitterKeyMockMvc.perform(get("/api/twitter-keys/{id}", twitterKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(twitterKey.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.consumerKey").value(DEFAULT_CONSUMER_KEY.toString()))
            .andExpect(jsonPath("$.consumerSecret").value(DEFAULT_CONSUMER_SECRET.toString()))
            .andExpect(jsonPath("$.accessToken").value(DEFAULT_ACCESS_TOKEN.toString()))
            .andExpect(jsonPath("$.accessTokenSecret").value(DEFAULT_ACCESS_TOKEN_SECRET.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTwitterKey() throws Exception {
        // Get the twitterKey
        restTwitterKeyMockMvc.perform(get("/api/twitter-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTwitterKey() throws Exception {
        // Initialize the database
        twitterKeyService.save(twitterKey);

        int databaseSizeBeforeUpdate = twitterKeyRepository.findAll().size();

        // Update the twitterKey
        TwitterKey updatedTwitterKey = twitterKeyRepository.findOne(twitterKey.getId());
        // Disconnect from session so that the updates on updatedTwitterKey are not directly saved in db
        em.detach(updatedTwitterKey);
        updatedTwitterKey
            .name(UPDATED_NAME)
            .consumerKey(UPDATED_CONSUMER_KEY)
            .consumerSecret(UPDATED_CONSUMER_SECRET)
            .accessToken(UPDATED_ACCESS_TOKEN)
            .accessTokenSecret(UPDATED_ACCESS_TOKEN_SECRET)
            .active(UPDATED_ACTIVE);

        restTwitterKeyMockMvc.perform(put("/api/twitter-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTwitterKey)))
            .andExpect(status().isOk());

        // Validate the TwitterKey in the database
        List<TwitterKey> twitterKeyList = twitterKeyRepository.findAll();
        assertThat(twitterKeyList).hasSize(databaseSizeBeforeUpdate);
        TwitterKey testTwitterKey = twitterKeyList.get(twitterKeyList.size() - 1);
        assertThat(testTwitterKey.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTwitterKey.getConsumerKey()).isEqualTo(UPDATED_CONSUMER_KEY);
        assertThat(testTwitterKey.getConsumerSecret()).isEqualTo(UPDATED_CONSUMER_SECRET);
        assertThat(testTwitterKey.getAccessToken()).isEqualTo(UPDATED_ACCESS_TOKEN);
        assertThat(testTwitterKey.getAccessTokenSecret()).isEqualTo(UPDATED_ACCESS_TOKEN_SECRET);
        assertThat(testTwitterKey.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the TwitterKey in Elasticsearch
        TwitterKey twitterKeyEs = twitterKeySearchRepository.findOne(testTwitterKey.getId());
        assertThat(twitterKeyEs).isEqualToIgnoringGivenFields(testTwitterKey);
    }

    @Test
    @Transactional
    public void updateNonExistingTwitterKey() throws Exception {
        int databaseSizeBeforeUpdate = twitterKeyRepository.findAll().size();

        // Create the TwitterKey

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTwitterKeyMockMvc.perform(put("/api/twitter-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(twitterKey)))
            .andExpect(status().isCreated());

        // Validate the TwitterKey in the database
        List<TwitterKey> twitterKeyList = twitterKeyRepository.findAll();
        assertThat(twitterKeyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTwitterKey() throws Exception {
        // Initialize the database
        twitterKeyService.save(twitterKey);

        int databaseSizeBeforeDelete = twitterKeyRepository.findAll().size();

        // Get the twitterKey
        restTwitterKeyMockMvc.perform(delete("/api/twitter-keys/{id}", twitterKey.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean twitterKeyExistsInEs = twitterKeySearchRepository.exists(twitterKey.getId());
        assertThat(twitterKeyExistsInEs).isFalse();

        // Validate the database is empty
        List<TwitterKey> twitterKeyList = twitterKeyRepository.findAll();
        assertThat(twitterKeyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTwitterKey() throws Exception {
        // Initialize the database
        twitterKeyService.save(twitterKey);

        // Search the twitterKey
        restTwitterKeyMockMvc.perform(get("/api/_search/twitter-keys?query=id:" + twitterKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(twitterKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].consumerKey").value(hasItem(DEFAULT_CONSUMER_KEY.toString())))
            .andExpect(jsonPath("$.[*].consumerSecret").value(hasItem(DEFAULT_CONSUMER_SECRET.toString())))
            .andExpect(jsonPath("$.[*].accessToken").value(hasItem(DEFAULT_ACCESS_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].accessTokenSecret").value(hasItem(DEFAULT_ACCESS_TOKEN_SECRET.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TwitterKey.class);
        TwitterKey twitterKey1 = new TwitterKey();
        twitterKey1.setId(1L);
        TwitterKey twitterKey2 = new TwitterKey();
        twitterKey2.setId(twitterKey1.getId());
        assertThat(twitterKey1).isEqualTo(twitterKey2);
        twitterKey2.setId(2L);
        assertThat(twitterKey1).isNotEqualTo(twitterKey2);
        twitterKey1.setId(null);
        assertThat(twitterKey1).isNotEqualTo(twitterKey2);
    }
}
