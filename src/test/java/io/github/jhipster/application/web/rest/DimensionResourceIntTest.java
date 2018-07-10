package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Dimension;
import io.github.jhipster.application.repository.DimensionRepository;
import io.github.jhipster.application.repository.search.DimensionSearchRepository;
import io.github.jhipster.application.service.DimensionService;
import io.github.jhipster.application.service.dto.DimensionDTO;
import io.github.jhipster.application.service.mapper.DimensionMapper;
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

import io.github.jhipster.application.domain.enumeration.UnitSystem;
/**
 * Test class for the DimensionResource REST controller.
 *
 * @see DimensionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DimensionResourceIntTest {

    private static final UnitSystem DEFAULT_US = UnitSystem.MM;
    private static final UnitSystem UPDATED_US = UnitSystem.INCH;

    private static final Double DEFAULT_X = 1D;
    private static final Double UPDATED_X = 2D;

    private static final Double DEFAULT_Y = 1D;
    private static final Double UPDATED_Y = 2D;

    private static final Double DEFAULT_Z = 1D;
    private static final Double UPDATED_Z = 2D;

    @Autowired
    private DimensionRepository dimensionRepository;


    @Autowired
    private DimensionMapper dimensionMapper;
    

    @Autowired
    private DimensionService dimensionService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.DimensionSearchRepositoryMockConfiguration
     */
    @Autowired
    private DimensionSearchRepository mockDimensionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDimensionMockMvc;

    private Dimension dimension;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DimensionResource dimensionResource = new DimensionResource(dimensionService);
        this.restDimensionMockMvc = MockMvcBuilders.standaloneSetup(dimensionResource)
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
    public static Dimension createEntity(EntityManager em) {
        Dimension dimension = new Dimension()
            .us(DEFAULT_US)
            .x(DEFAULT_X)
            .y(DEFAULT_Y)
            .z(DEFAULT_Z);
        return dimension;
    }

    @Before
    public void initTest() {
        dimension = createEntity(em);
    }

    @Test
    @Transactional
    public void createDimension() throws Exception {
        int databaseSizeBeforeCreate = dimensionRepository.findAll().size();

        // Create the Dimension
        DimensionDTO dimensionDTO = dimensionMapper.toDto(dimension);
        restDimensionMockMvc.perform(post("/api/dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimensionDTO)))
            .andExpect(status().isCreated());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeCreate + 1);
        Dimension testDimension = dimensionList.get(dimensionList.size() - 1);
        assertThat(testDimension.getUs()).isEqualTo(DEFAULT_US);
        assertThat(testDimension.getX()).isEqualTo(DEFAULT_X);
        assertThat(testDimension.getY()).isEqualTo(DEFAULT_Y);
        assertThat(testDimension.getZ()).isEqualTo(DEFAULT_Z);

        // Validate the Dimension in Elasticsearch
        verify(mockDimensionSearchRepository, times(1)).save(testDimension);
    }

    @Test
    @Transactional
    public void createDimensionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dimensionRepository.findAll().size();

        // Create the Dimension with an existing ID
        dimension.setId(1L);
        DimensionDTO dimensionDTO = dimensionMapper.toDto(dimension);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDimensionMockMvc.perform(post("/api/dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimensionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Dimension in Elasticsearch
        verify(mockDimensionSearchRepository, times(0)).save(dimension);
    }

    @Test
    @Transactional
    public void getAllDimensions() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList
        restDimensionMockMvc.perform(get("/api/dimensions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimension.getId().intValue())))
            .andExpect(jsonPath("$.[*].us").value(hasItem(DEFAULT_US.toString())))
            .andExpect(jsonPath("$.[*].x").value(hasItem(DEFAULT_X.doubleValue())))
            .andExpect(jsonPath("$.[*].y").value(hasItem(DEFAULT_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].z").value(hasItem(DEFAULT_Z.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getDimension() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get the dimension
        restDimensionMockMvc.perform(get("/api/dimensions/{id}", dimension.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dimension.getId().intValue()))
            .andExpect(jsonPath("$.us").value(DEFAULT_US.toString()))
            .andExpect(jsonPath("$.x").value(DEFAULT_X.doubleValue()))
            .andExpect(jsonPath("$.y").value(DEFAULT_Y.doubleValue()))
            .andExpect(jsonPath("$.z").value(DEFAULT_Z.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDimension() throws Exception {
        // Get the dimension
        restDimensionMockMvc.perform(get("/api/dimensions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDimension() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Update the dimension
        Dimension updatedDimension = dimensionRepository.findById(dimension.getId()).get();
        // Disconnect from session so that the updates on updatedDimension are not directly saved in db
        em.detach(updatedDimension);
        updatedDimension
            .us(UPDATED_US)
            .x(UPDATED_X)
            .y(UPDATED_Y)
            .z(UPDATED_Z);
        DimensionDTO dimensionDTO = dimensionMapper.toDto(updatedDimension);

        restDimensionMockMvc.perform(put("/api/dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimensionDTO)))
            .andExpect(status().isOk());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
        Dimension testDimension = dimensionList.get(dimensionList.size() - 1);
        assertThat(testDimension.getUs()).isEqualTo(UPDATED_US);
        assertThat(testDimension.getX()).isEqualTo(UPDATED_X);
        assertThat(testDimension.getY()).isEqualTo(UPDATED_Y);
        assertThat(testDimension.getZ()).isEqualTo(UPDATED_Z);

        // Validate the Dimension in Elasticsearch
        verify(mockDimensionSearchRepository, times(1)).save(testDimension);
    }

    @Test
    @Transactional
    public void updateNonExistingDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Create the Dimension
        DimensionDTO dimensionDTO = dimensionMapper.toDto(dimension);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDimensionMockMvc.perform(put("/api/dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimensionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Dimension in Elasticsearch
        verify(mockDimensionSearchRepository, times(0)).save(dimension);
    }

    @Test
    @Transactional
    public void deleteDimension() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeDelete = dimensionRepository.findAll().size();

        // Get the dimension
        restDimensionMockMvc.perform(delete("/api/dimensions/{id}", dimension.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Dimension in Elasticsearch
        verify(mockDimensionSearchRepository, times(1)).deleteById(dimension.getId());
    }

    @Test
    @Transactional
    public void searchDimension() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);
        when(mockDimensionSearchRepository.search(queryStringQuery("id:" + dimension.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dimension), PageRequest.of(0, 1), 1));
        // Search the dimension
        restDimensionMockMvc.perform(get("/api/_search/dimensions?query=id:" + dimension.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimension.getId().intValue())))
            .andExpect(jsonPath("$.[*].us").value(hasItem(DEFAULT_US.toString())))
            .andExpect(jsonPath("$.[*].x").value(hasItem(DEFAULT_X.doubleValue())))
            .andExpect(jsonPath("$.[*].y").value(hasItem(DEFAULT_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].z").value(hasItem(DEFAULT_Z.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dimension.class);
        Dimension dimension1 = new Dimension();
        dimension1.setId(1L);
        Dimension dimension2 = new Dimension();
        dimension2.setId(dimension1.getId());
        assertThat(dimension1).isEqualTo(dimension2);
        dimension2.setId(2L);
        assertThat(dimension1).isNotEqualTo(dimension2);
        dimension1.setId(null);
        assertThat(dimension1).isNotEqualTo(dimension2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DimensionDTO.class);
        DimensionDTO dimensionDTO1 = new DimensionDTO();
        dimensionDTO1.setId(1L);
        DimensionDTO dimensionDTO2 = new DimensionDTO();
        assertThat(dimensionDTO1).isNotEqualTo(dimensionDTO2);
        dimensionDTO2.setId(dimensionDTO1.getId());
        assertThat(dimensionDTO1).isEqualTo(dimensionDTO2);
        dimensionDTO2.setId(2L);
        assertThat(dimensionDTO1).isNotEqualTo(dimensionDTO2);
        dimensionDTO1.setId(null);
        assertThat(dimensionDTO1).isNotEqualTo(dimensionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dimensionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dimensionMapper.fromId(null)).isNull();
    }
}
