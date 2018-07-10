package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.TechnologyClass;
import io.github.jhipster.application.repository.TechnologyClassRepository;
import io.github.jhipster.application.repository.search.TechnologyClassSearchRepository;
import io.github.jhipster.application.service.TechnologyClassService;
import io.github.jhipster.application.service.dto.TechnologyClassDTO;
import io.github.jhipster.application.service.mapper.TechnologyClassMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TechnologyClassResource REST controller.
 *
 * @see TechnologyClassResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TechnologyClassResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SHOW_ACCURACY = false;
    private static final Boolean UPDATED_SHOW_ACCURACY = true;

    private static final Boolean DEFAULT_SHOW_PORTABLE = false;
    private static final Boolean UPDATED_SHOW_PORTABLE = true;

    private static final Boolean DEFAULT_SHOW_MATERIAL = false;
    private static final Boolean UPDATED_SHOW_MATERIAL = true;

    @Autowired
    private TechnologyClassRepository technologyClassRepository;


    @Autowired
    private TechnologyClassMapper technologyClassMapper;
    

    @Autowired
    private TechnologyClassService technologyClassService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.TechnologyClassSearchRepositoryMockConfiguration
     */
    @Autowired
    private TechnologyClassSearchRepository mockTechnologyClassSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTechnologyClassMockMvc;

    private TechnologyClass technologyClass;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TechnologyClassResource technologyClassResource = new TechnologyClassResource(technologyClassService);
        this.restTechnologyClassMockMvc = MockMvcBuilders.standaloneSetup(technologyClassResource)
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
    public static TechnologyClass createEntity(EntityManager em) {
        TechnologyClass technologyClass = new TechnologyClass()
            .name(DEFAULT_NAME)
            .showAccuracy(DEFAULT_SHOW_ACCURACY)
            .showPortable(DEFAULT_SHOW_PORTABLE)
            .showMaterial(DEFAULT_SHOW_MATERIAL);
        return technologyClass;
    }

    @Before
    public void initTest() {
        technologyClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechnologyClass() throws Exception {
        int databaseSizeBeforeCreate = technologyClassRepository.findAll().size();

        // Create the TechnologyClass
        TechnologyClassDTO technologyClassDTO = technologyClassMapper.toDto(technologyClass);
        restTechnologyClassMockMvc.perform(post("/api/technology-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(technologyClassDTO)))
            .andExpect(status().isCreated());

        // Validate the TechnologyClass in the database
        List<TechnologyClass> technologyClassList = technologyClassRepository.findAll();
        assertThat(technologyClassList).hasSize(databaseSizeBeforeCreate + 1);
        TechnologyClass testTechnologyClass = technologyClassList.get(technologyClassList.size() - 1);
        assertThat(testTechnologyClass.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTechnologyClass.isShowAccuracy()).isEqualTo(DEFAULT_SHOW_ACCURACY);
        assertThat(testTechnologyClass.isShowPortable()).isEqualTo(DEFAULT_SHOW_PORTABLE);
        assertThat(testTechnologyClass.isShowMaterial()).isEqualTo(DEFAULT_SHOW_MATERIAL);

        // Validate the TechnologyClass in Elasticsearch
        verify(mockTechnologyClassSearchRepository, times(1)).save(testTechnologyClass);
    }

    @Test
    @Transactional
    public void createTechnologyClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = technologyClassRepository.findAll().size();

        // Create the TechnologyClass with an existing ID
        technologyClass.setId(1L);
        TechnologyClassDTO technologyClassDTO = technologyClassMapper.toDto(technologyClass);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechnologyClassMockMvc.perform(post("/api/technology-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(technologyClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechnologyClass in the database
        List<TechnologyClass> technologyClassList = technologyClassRepository.findAll();
        assertThat(technologyClassList).hasSize(databaseSizeBeforeCreate);

        // Validate the TechnologyClass in Elasticsearch
        verify(mockTechnologyClassSearchRepository, times(0)).save(technologyClass);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = technologyClassRepository.findAll().size();
        // set the field null
        technologyClass.setName(null);

        // Create the TechnologyClass, which fails.
        TechnologyClassDTO technologyClassDTO = technologyClassMapper.toDto(technologyClass);

        restTechnologyClassMockMvc.perform(post("/api/technology-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(technologyClassDTO)))
            .andExpect(status().isBadRequest());

        List<TechnologyClass> technologyClassList = technologyClassRepository.findAll();
        assertThat(technologyClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTechnologyClasses() throws Exception {
        // Initialize the database
        technologyClassRepository.saveAndFlush(technologyClass);

        // Get all the technologyClassList
        restTechnologyClassMockMvc.perform(get("/api/technology-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(technologyClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].showAccuracy").value(hasItem(DEFAULT_SHOW_ACCURACY.booleanValue())))
            .andExpect(jsonPath("$.[*].showPortable").value(hasItem(DEFAULT_SHOW_PORTABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].showMaterial").value(hasItem(DEFAULT_SHOW_MATERIAL.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getTechnologyClass() throws Exception {
        // Initialize the database
        technologyClassRepository.saveAndFlush(technologyClass);

        // Get the technologyClass
        restTechnologyClassMockMvc.perform(get("/api/technology-classes/{id}", technologyClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(technologyClass.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.showAccuracy").value(DEFAULT_SHOW_ACCURACY.booleanValue()))
            .andExpect(jsonPath("$.showPortable").value(DEFAULT_SHOW_PORTABLE.booleanValue()))
            .andExpect(jsonPath("$.showMaterial").value(DEFAULT_SHOW_MATERIAL.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTechnologyClass() throws Exception {
        // Get the technologyClass
        restTechnologyClassMockMvc.perform(get("/api/technology-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechnologyClass() throws Exception {
        // Initialize the database
        technologyClassRepository.saveAndFlush(technologyClass);

        int databaseSizeBeforeUpdate = technologyClassRepository.findAll().size();

        // Update the technologyClass
        TechnologyClass updatedTechnologyClass = technologyClassRepository.findById(technologyClass.getId()).get();
        // Disconnect from session so that the updates on updatedTechnologyClass are not directly saved in db
        em.detach(updatedTechnologyClass);
        updatedTechnologyClass
            .name(UPDATED_NAME)
            .showAccuracy(UPDATED_SHOW_ACCURACY)
            .showPortable(UPDATED_SHOW_PORTABLE)
            .showMaterial(UPDATED_SHOW_MATERIAL);
        TechnologyClassDTO technologyClassDTO = technologyClassMapper.toDto(updatedTechnologyClass);

        restTechnologyClassMockMvc.perform(put("/api/technology-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(technologyClassDTO)))
            .andExpect(status().isOk());

        // Validate the TechnologyClass in the database
        List<TechnologyClass> technologyClassList = technologyClassRepository.findAll();
        assertThat(technologyClassList).hasSize(databaseSizeBeforeUpdate);
        TechnologyClass testTechnologyClass = technologyClassList.get(technologyClassList.size() - 1);
        assertThat(testTechnologyClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTechnologyClass.isShowAccuracy()).isEqualTo(UPDATED_SHOW_ACCURACY);
        assertThat(testTechnologyClass.isShowPortable()).isEqualTo(UPDATED_SHOW_PORTABLE);
        assertThat(testTechnologyClass.isShowMaterial()).isEqualTo(UPDATED_SHOW_MATERIAL);

        // Validate the TechnologyClass in Elasticsearch
        verify(mockTechnologyClassSearchRepository, times(1)).save(testTechnologyClass);
    }

    @Test
    @Transactional
    public void updateNonExistingTechnologyClass() throws Exception {
        int databaseSizeBeforeUpdate = technologyClassRepository.findAll().size();

        // Create the TechnologyClass
        TechnologyClassDTO technologyClassDTO = technologyClassMapper.toDto(technologyClass);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTechnologyClassMockMvc.perform(put("/api/technology-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(technologyClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechnologyClass in the database
        List<TechnologyClass> technologyClassList = technologyClassRepository.findAll();
        assertThat(technologyClassList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TechnologyClass in Elasticsearch
        verify(mockTechnologyClassSearchRepository, times(0)).save(technologyClass);
    }

    @Test
    @Transactional
    public void deleteTechnologyClass() throws Exception {
        // Initialize the database
        technologyClassRepository.saveAndFlush(technologyClass);

        int databaseSizeBeforeDelete = technologyClassRepository.findAll().size();

        // Get the technologyClass
        restTechnologyClassMockMvc.perform(delete("/api/technology-classes/{id}", technologyClass.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TechnologyClass> technologyClassList = technologyClassRepository.findAll();
        assertThat(technologyClassList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TechnologyClass in Elasticsearch
        verify(mockTechnologyClassSearchRepository, times(1)).deleteById(technologyClass.getId());
    }

    @Test
    @Transactional
    public void searchTechnologyClass() throws Exception {
        // Initialize the database
        technologyClassRepository.saveAndFlush(technologyClass);
        when(mockTechnologyClassSearchRepository.search(queryStringQuery("id:" + technologyClass.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(technologyClass), PageRequest.of(0, 1), 1));
        // Search the technologyClass
        restTechnologyClassMockMvc.perform(get("/api/_search/technology-classes?query=id:" + technologyClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(technologyClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].showAccuracy").value(hasItem(DEFAULT_SHOW_ACCURACY.booleanValue())))
            .andExpect(jsonPath("$.[*].showPortable").value(hasItem(DEFAULT_SHOW_PORTABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].showMaterial").value(hasItem(DEFAULT_SHOW_MATERIAL.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechnologyClass.class);
        TechnologyClass technologyClass1 = new TechnologyClass();
        technologyClass1.setId(1L);
        TechnologyClass technologyClass2 = new TechnologyClass();
        technologyClass2.setId(technologyClass1.getId());
        assertThat(technologyClass1).isEqualTo(technologyClass2);
        technologyClass2.setId(2L);
        assertThat(technologyClass1).isNotEqualTo(technologyClass2);
        technologyClass1.setId(null);
        assertThat(technologyClass1).isNotEqualTo(technologyClass2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechnologyClassDTO.class);
        TechnologyClassDTO technologyClassDTO1 = new TechnologyClassDTO();
        technologyClassDTO1.setId(1L);
        TechnologyClassDTO technologyClassDTO2 = new TechnologyClassDTO();
        assertThat(technologyClassDTO1).isNotEqualTo(technologyClassDTO2);
        technologyClassDTO2.setId(technologyClassDTO1.getId());
        assertThat(technologyClassDTO1).isEqualTo(technologyClassDTO2);
        technologyClassDTO2.setId(2L);
        assertThat(technologyClassDTO1).isNotEqualTo(technologyClassDTO2);
        technologyClassDTO1.setId(null);
        assertThat(technologyClassDTO1).isNotEqualTo(technologyClassDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(technologyClassMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(technologyClassMapper.fromId(null)).isNull();
    }
}
