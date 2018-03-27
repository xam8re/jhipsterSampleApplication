package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.HodKey;
import io.github.jhipster.application.repository.HodKeyRepository;
import io.github.jhipster.application.service.HodKeyService;
import io.github.jhipster.application.repository.search.HodKeySearchRepository;
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
 * Test class for the HodKeyResource REST controller.
 *
 * @see HodKeyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class HodKeyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HODKEY = "AAAAAAAAAA";
    private static final String UPDATED_HODKEY = "BBBBBBBBBB";

    @Autowired
    private HodKeyRepository hodKeyRepository;

    @Autowired
    private HodKeyService hodKeyService;

    @Autowired
    private HodKeySearchRepository hodKeySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHodKeyMockMvc;

    private HodKey hodKey;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HodKeyResource hodKeyResource = new HodKeyResource(hodKeyService);
        this.restHodKeyMockMvc = MockMvcBuilders.standaloneSetup(hodKeyResource)
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
    public static HodKey createEntity(EntityManager em) {
        HodKey hodKey = new HodKey()
            .name(DEFAULT_NAME)
            .hodkey(DEFAULT_HODKEY);
        return hodKey;
    }

    @Before
    public void initTest() {
        hodKeySearchRepository.deleteAll();
        hodKey = createEntity(em);
    }

    @Test
    @Transactional
    public void createHodKey() throws Exception {
        int databaseSizeBeforeCreate = hodKeyRepository.findAll().size();

        // Create the HodKey
        restHodKeyMockMvc.perform(post("/api/hod-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hodKey)))
            .andExpect(status().isCreated());

        // Validate the HodKey in the database
        List<HodKey> hodKeyList = hodKeyRepository.findAll();
        assertThat(hodKeyList).hasSize(databaseSizeBeforeCreate + 1);
        HodKey testHodKey = hodKeyList.get(hodKeyList.size() - 1);
        assertThat(testHodKey.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHodKey.getHodkey()).isEqualTo(DEFAULT_HODKEY);

        // Validate the HodKey in Elasticsearch
        HodKey hodKeyEs = hodKeySearchRepository.findOne(testHodKey.getId());
        assertThat(hodKeyEs).isEqualToIgnoringGivenFields(testHodKey);
    }

    @Test
    @Transactional
    public void createHodKeyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hodKeyRepository.findAll().size();

        // Create the HodKey with an existing ID
        hodKey.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHodKeyMockMvc.perform(post("/api/hod-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hodKey)))
            .andExpect(status().isBadRequest());

        // Validate the HodKey in the database
        List<HodKey> hodKeyList = hodKeyRepository.findAll();
        assertThat(hodKeyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hodKeyRepository.findAll().size();
        // set the field null
        hodKey.setName(null);

        // Create the HodKey, which fails.

        restHodKeyMockMvc.perform(post("/api/hod-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hodKey)))
            .andExpect(status().isBadRequest());

        List<HodKey> hodKeyList = hodKeyRepository.findAll();
        assertThat(hodKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHodkeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = hodKeyRepository.findAll().size();
        // set the field null
        hodKey.setHodkey(null);

        // Create the HodKey, which fails.

        restHodKeyMockMvc.perform(post("/api/hod-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hodKey)))
            .andExpect(status().isBadRequest());

        List<HodKey> hodKeyList = hodKeyRepository.findAll();
        assertThat(hodKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHodKeys() throws Exception {
        // Initialize the database
        hodKeyRepository.saveAndFlush(hodKey);

        // Get all the hodKeyList
        restHodKeyMockMvc.perform(get("/api/hod-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hodKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].hodkey").value(hasItem(DEFAULT_HODKEY.toString())));
    }

    @Test
    @Transactional
    public void getHodKey() throws Exception {
        // Initialize the database
        hodKeyRepository.saveAndFlush(hodKey);

        // Get the hodKey
        restHodKeyMockMvc.perform(get("/api/hod-keys/{id}", hodKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hodKey.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.hodkey").value(DEFAULT_HODKEY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHodKey() throws Exception {
        // Get the hodKey
        restHodKeyMockMvc.perform(get("/api/hod-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHodKey() throws Exception {
        // Initialize the database
        hodKeyService.save(hodKey);

        int databaseSizeBeforeUpdate = hodKeyRepository.findAll().size();

        // Update the hodKey
        HodKey updatedHodKey = hodKeyRepository.findOne(hodKey.getId());
        // Disconnect from session so that the updates on updatedHodKey are not directly saved in db
        em.detach(updatedHodKey);
        updatedHodKey
            .name(UPDATED_NAME)
            .hodkey(UPDATED_HODKEY);

        restHodKeyMockMvc.perform(put("/api/hod-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHodKey)))
            .andExpect(status().isOk());

        // Validate the HodKey in the database
        List<HodKey> hodKeyList = hodKeyRepository.findAll();
        assertThat(hodKeyList).hasSize(databaseSizeBeforeUpdate);
        HodKey testHodKey = hodKeyList.get(hodKeyList.size() - 1);
        assertThat(testHodKey.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHodKey.getHodkey()).isEqualTo(UPDATED_HODKEY);

        // Validate the HodKey in Elasticsearch
        HodKey hodKeyEs = hodKeySearchRepository.findOne(testHodKey.getId());
        assertThat(hodKeyEs).isEqualToIgnoringGivenFields(testHodKey);
    }

    @Test
    @Transactional
    public void updateNonExistingHodKey() throws Exception {
        int databaseSizeBeforeUpdate = hodKeyRepository.findAll().size();

        // Create the HodKey

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHodKeyMockMvc.perform(put("/api/hod-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hodKey)))
            .andExpect(status().isCreated());

        // Validate the HodKey in the database
        List<HodKey> hodKeyList = hodKeyRepository.findAll();
        assertThat(hodKeyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHodKey() throws Exception {
        // Initialize the database
        hodKeyService.save(hodKey);

        int databaseSizeBeforeDelete = hodKeyRepository.findAll().size();

        // Get the hodKey
        restHodKeyMockMvc.perform(delete("/api/hod-keys/{id}", hodKey.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hodKeyExistsInEs = hodKeySearchRepository.exists(hodKey.getId());
        assertThat(hodKeyExistsInEs).isFalse();

        // Validate the database is empty
        List<HodKey> hodKeyList = hodKeyRepository.findAll();
        assertThat(hodKeyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHodKey() throws Exception {
        // Initialize the database
        hodKeyService.save(hodKey);

        // Search the hodKey
        restHodKeyMockMvc.perform(get("/api/_search/hod-keys?query=id:" + hodKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hodKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].hodkey").value(hasItem(DEFAULT_HODKEY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HodKey.class);
        HodKey hodKey1 = new HodKey();
        hodKey1.setId(1L);
        HodKey hodKey2 = new HodKey();
        hodKey2.setId(hodKey1.getId());
        assertThat(hodKey1).isEqualTo(hodKey2);
        hodKey2.setId(2L);
        assertThat(hodKey1).isNotEqualTo(hodKey2);
        hodKey1.setId(null);
        assertThat(hodKey1).isNotEqualTo(hodKey2);
    }
}
