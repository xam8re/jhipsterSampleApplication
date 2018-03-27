package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.TweetCategory;
import io.github.jhipster.application.repository.TweetCategoryRepository;
import io.github.jhipster.application.service.TweetCategoryService;
import io.github.jhipster.application.repository.search.TweetCategorySearchRepository;
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
 * Test class for the TweetCategoryResource REST controller.
 *
 * @see TweetCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TweetCategoryResourceIntTest {

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    @Autowired
    private TweetCategoryRepository tweetCategoryRepository;

    @Autowired
    private TweetCategoryService tweetCategoryService;

    @Autowired
    private TweetCategorySearchRepository tweetCategorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTweetCategoryMockMvc;

    private TweetCategory tweetCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TweetCategoryResource tweetCategoryResource = new TweetCategoryResource(tweetCategoryService);
        this.restTweetCategoryMockMvc = MockMvcBuilders.standaloneSetup(tweetCategoryResource)
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
    public static TweetCategory createEntity(EntityManager em) {
        TweetCategory tweetCategory = new TweetCategory()
            .categoria(DEFAULT_CATEGORIA);
        return tweetCategory;
    }

    @Before
    public void initTest() {
        tweetCategorySearchRepository.deleteAll();
        tweetCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTweetCategory() throws Exception {
        int databaseSizeBeforeCreate = tweetCategoryRepository.findAll().size();

        // Create the TweetCategory
        restTweetCategoryMockMvc.perform(post("/api/tweet-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweetCategory)))
            .andExpect(status().isCreated());

        // Validate the TweetCategory in the database
        List<TweetCategory> tweetCategoryList = tweetCategoryRepository.findAll();
        assertThat(tweetCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        TweetCategory testTweetCategory = tweetCategoryList.get(tweetCategoryList.size() - 1);
        assertThat(testTweetCategory.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);

        // Validate the TweetCategory in Elasticsearch
        TweetCategory tweetCategoryEs = tweetCategorySearchRepository.findOne(testTweetCategory.getId());
        assertThat(tweetCategoryEs).isEqualToIgnoringGivenFields(testTweetCategory);
    }

    @Test
    @Transactional
    public void createTweetCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tweetCategoryRepository.findAll().size();

        // Create the TweetCategory with an existing ID
        tweetCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTweetCategoryMockMvc.perform(post("/api/tweet-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweetCategory)))
            .andExpect(status().isBadRequest());

        // Validate the TweetCategory in the database
        List<TweetCategory> tweetCategoryList = tweetCategoryRepository.findAll();
        assertThat(tweetCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCategoriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tweetCategoryRepository.findAll().size();
        // set the field null
        tweetCategory.setCategoria(null);

        // Create the TweetCategory, which fails.

        restTweetCategoryMockMvc.perform(post("/api/tweet-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweetCategory)))
            .andExpect(status().isBadRequest());

        List<TweetCategory> tweetCategoryList = tweetCategoryRepository.findAll();
        assertThat(tweetCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTweetCategories() throws Exception {
        // Initialize the database
        tweetCategoryRepository.saveAndFlush(tweetCategory);

        // Get all the tweetCategoryList
        restTweetCategoryMockMvc.perform(get("/api/tweet-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tweetCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())));
    }

    @Test
    @Transactional
    public void getTweetCategory() throws Exception {
        // Initialize the database
        tweetCategoryRepository.saveAndFlush(tweetCategory);

        // Get the tweetCategory
        restTweetCategoryMockMvc.perform(get("/api/tweet-categories/{id}", tweetCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tweetCategory.getId().intValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTweetCategory() throws Exception {
        // Get the tweetCategory
        restTweetCategoryMockMvc.perform(get("/api/tweet-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTweetCategory() throws Exception {
        // Initialize the database
        tweetCategoryService.save(tweetCategory);

        int databaseSizeBeforeUpdate = tweetCategoryRepository.findAll().size();

        // Update the tweetCategory
        TweetCategory updatedTweetCategory = tweetCategoryRepository.findOne(tweetCategory.getId());
        // Disconnect from session so that the updates on updatedTweetCategory are not directly saved in db
        em.detach(updatedTweetCategory);
        updatedTweetCategory
            .categoria(UPDATED_CATEGORIA);

        restTweetCategoryMockMvc.perform(put("/api/tweet-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTweetCategory)))
            .andExpect(status().isOk());

        // Validate the TweetCategory in the database
        List<TweetCategory> tweetCategoryList = tweetCategoryRepository.findAll();
        assertThat(tweetCategoryList).hasSize(databaseSizeBeforeUpdate);
        TweetCategory testTweetCategory = tweetCategoryList.get(tweetCategoryList.size() - 1);
        assertThat(testTweetCategory.getCategoria()).isEqualTo(UPDATED_CATEGORIA);

        // Validate the TweetCategory in Elasticsearch
        TweetCategory tweetCategoryEs = tweetCategorySearchRepository.findOne(testTweetCategory.getId());
        assertThat(tweetCategoryEs).isEqualToIgnoringGivenFields(testTweetCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingTweetCategory() throws Exception {
        int databaseSizeBeforeUpdate = tweetCategoryRepository.findAll().size();

        // Create the TweetCategory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTweetCategoryMockMvc.perform(put("/api/tweet-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweetCategory)))
            .andExpect(status().isCreated());

        // Validate the TweetCategory in the database
        List<TweetCategory> tweetCategoryList = tweetCategoryRepository.findAll();
        assertThat(tweetCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTweetCategory() throws Exception {
        // Initialize the database
        tweetCategoryService.save(tweetCategory);

        int databaseSizeBeforeDelete = tweetCategoryRepository.findAll().size();

        // Get the tweetCategory
        restTweetCategoryMockMvc.perform(delete("/api/tweet-categories/{id}", tweetCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tweetCategoryExistsInEs = tweetCategorySearchRepository.exists(tweetCategory.getId());
        assertThat(tweetCategoryExistsInEs).isFalse();

        // Validate the database is empty
        List<TweetCategory> tweetCategoryList = tweetCategoryRepository.findAll();
        assertThat(tweetCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTweetCategory() throws Exception {
        // Initialize the database
        tweetCategoryService.save(tweetCategory);

        // Search the tweetCategory
        restTweetCategoryMockMvc.perform(get("/api/_search/tweet-categories?query=id:" + tweetCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tweetCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TweetCategory.class);
        TweetCategory tweetCategory1 = new TweetCategory();
        tweetCategory1.setId(1L);
        TweetCategory tweetCategory2 = new TweetCategory();
        tweetCategory2.setId(tweetCategory1.getId());
        assertThat(tweetCategory1).isEqualTo(tweetCategory2);
        tweetCategory2.setId(2L);
        assertThat(tweetCategory1).isNotEqualTo(tweetCategory2);
        tweetCategory1.setId(null);
        assertThat(tweetCategory1).isNotEqualTo(tweetCategory2);
    }
}
