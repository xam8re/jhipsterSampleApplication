package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.MachineModel;
import io.github.jhipster.application.domain.Technology;
import io.github.jhipster.application.domain.Manufacturer;
import io.github.jhipster.application.repository.MachineModelRepository;
import io.github.jhipster.application.repository.search.MachineModelSearchRepository;
import io.github.jhipster.application.service.MachineModelService;
import io.github.jhipster.application.service.dto.MachineModelDTO;
import io.github.jhipster.application.service.mapper.MachineModelMapper;
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
 * Test class for the MachineModelResource REST controller.
 *
 * @see MachineModelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class MachineModelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MachineModelRepository machineModelRepository;


    @Autowired
    private MachineModelMapper machineModelMapper;
    

    @Autowired
    private MachineModelService machineModelService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.MachineModelSearchRepositoryMockConfiguration
     */
    @Autowired
    private MachineModelSearchRepository mockMachineModelSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMachineModelMockMvc;

    private MachineModel machineModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MachineModelResource machineModelResource = new MachineModelResource(machineModelService);
        this.restMachineModelMockMvc = MockMvcBuilders.standaloneSetup(machineModelResource)
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
    public static MachineModel createEntity(EntityManager em) {
        MachineModel machineModel = new MachineModel()
            .name(DEFAULT_NAME);
        // Add required entity
        Technology technology = TechnologyResourceIntTest.createEntity(em);
        em.persist(technology);
        em.flush();
        machineModel.getTechnologies().add(technology);
        // Add required entity
        Manufacturer manufacturer = ManufacturerResourceIntTest.createEntity(em);
        em.persist(manufacturer);
        em.flush();
        machineModel.setManufacturer(manufacturer);
        return machineModel;
    }

    @Before
    public void initTest() {
        machineModel = createEntity(em);
    }

    @Test
    @Transactional
    public void createMachineModel() throws Exception {
        int databaseSizeBeforeCreate = machineModelRepository.findAll().size();

        // Create the MachineModel
        MachineModelDTO machineModelDTO = machineModelMapper.toDto(machineModel);
        restMachineModelMockMvc.perform(post("/api/machine-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(machineModelDTO)))
            .andExpect(status().isCreated());

        // Validate the MachineModel in the database
        List<MachineModel> machineModelList = machineModelRepository.findAll();
        assertThat(machineModelList).hasSize(databaseSizeBeforeCreate + 1);
        MachineModel testMachineModel = machineModelList.get(machineModelList.size() - 1);
        assertThat(testMachineModel.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the MachineModel in Elasticsearch
        verify(mockMachineModelSearchRepository, times(1)).save(testMachineModel);
    }

    @Test
    @Transactional
    public void createMachineModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = machineModelRepository.findAll().size();

        // Create the MachineModel with an existing ID
        machineModel.setId(1L);
        MachineModelDTO machineModelDTO = machineModelMapper.toDto(machineModel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMachineModelMockMvc.perform(post("/api/machine-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(machineModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MachineModel in the database
        List<MachineModel> machineModelList = machineModelRepository.findAll();
        assertThat(machineModelList).hasSize(databaseSizeBeforeCreate);

        // Validate the MachineModel in Elasticsearch
        verify(mockMachineModelSearchRepository, times(0)).save(machineModel);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = machineModelRepository.findAll().size();
        // set the field null
        machineModel.setName(null);

        // Create the MachineModel, which fails.
        MachineModelDTO machineModelDTO = machineModelMapper.toDto(machineModel);

        restMachineModelMockMvc.perform(post("/api/machine-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(machineModelDTO)))
            .andExpect(status().isBadRequest());

        List<MachineModel> machineModelList = machineModelRepository.findAll();
        assertThat(machineModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMachineModels() throws Exception {
        // Initialize the database
        machineModelRepository.saveAndFlush(machineModel);

        // Get all the machineModelList
        restMachineModelMockMvc.perform(get("/api/machine-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(machineModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getMachineModel() throws Exception {
        // Initialize the database
        machineModelRepository.saveAndFlush(machineModel);

        // Get the machineModel
        restMachineModelMockMvc.perform(get("/api/machine-models/{id}", machineModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(machineModel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingMachineModel() throws Exception {
        // Get the machineModel
        restMachineModelMockMvc.perform(get("/api/machine-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMachineModel() throws Exception {
        // Initialize the database
        machineModelRepository.saveAndFlush(machineModel);

        int databaseSizeBeforeUpdate = machineModelRepository.findAll().size();

        // Update the machineModel
        MachineModel updatedMachineModel = machineModelRepository.findById(machineModel.getId()).get();
        // Disconnect from session so that the updates on updatedMachineModel are not directly saved in db
        em.detach(updatedMachineModel);
        updatedMachineModel
            .name(UPDATED_NAME);
        MachineModelDTO machineModelDTO = machineModelMapper.toDto(updatedMachineModel);

        restMachineModelMockMvc.perform(put("/api/machine-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(machineModelDTO)))
            .andExpect(status().isOk());

        // Validate the MachineModel in the database
        List<MachineModel> machineModelList = machineModelRepository.findAll();
        assertThat(machineModelList).hasSize(databaseSizeBeforeUpdate);
        MachineModel testMachineModel = machineModelList.get(machineModelList.size() - 1);
        assertThat(testMachineModel.getName()).isEqualTo(UPDATED_NAME);

        // Validate the MachineModel in Elasticsearch
        verify(mockMachineModelSearchRepository, times(1)).save(testMachineModel);
    }

    @Test
    @Transactional
    public void updateNonExistingMachineModel() throws Exception {
        int databaseSizeBeforeUpdate = machineModelRepository.findAll().size();

        // Create the MachineModel
        MachineModelDTO machineModelDTO = machineModelMapper.toDto(machineModel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMachineModelMockMvc.perform(put("/api/machine-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(machineModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MachineModel in the database
        List<MachineModel> machineModelList = machineModelRepository.findAll();
        assertThat(machineModelList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MachineModel in Elasticsearch
        verify(mockMachineModelSearchRepository, times(0)).save(machineModel);
    }

    @Test
    @Transactional
    public void deleteMachineModel() throws Exception {
        // Initialize the database
        machineModelRepository.saveAndFlush(machineModel);

        int databaseSizeBeforeDelete = machineModelRepository.findAll().size();

        // Get the machineModel
        restMachineModelMockMvc.perform(delete("/api/machine-models/{id}", machineModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MachineModel> machineModelList = machineModelRepository.findAll();
        assertThat(machineModelList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MachineModel in Elasticsearch
        verify(mockMachineModelSearchRepository, times(1)).deleteById(machineModel.getId());
    }

    @Test
    @Transactional
    public void searchMachineModel() throws Exception {
        // Initialize the database
        machineModelRepository.saveAndFlush(machineModel);
        when(mockMachineModelSearchRepository.search(queryStringQuery("id:" + machineModel.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(machineModel), PageRequest.of(0, 1), 1));
        // Search the machineModel
        restMachineModelMockMvc.perform(get("/api/_search/machine-models?query=id:" + machineModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(machineModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MachineModel.class);
        MachineModel machineModel1 = new MachineModel();
        machineModel1.setId(1L);
        MachineModel machineModel2 = new MachineModel();
        machineModel2.setId(machineModel1.getId());
        assertThat(machineModel1).isEqualTo(machineModel2);
        machineModel2.setId(2L);
        assertThat(machineModel1).isNotEqualTo(machineModel2);
        machineModel1.setId(null);
        assertThat(machineModel1).isNotEqualTo(machineModel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MachineModelDTO.class);
        MachineModelDTO machineModelDTO1 = new MachineModelDTO();
        machineModelDTO1.setId(1L);
        MachineModelDTO machineModelDTO2 = new MachineModelDTO();
        assertThat(machineModelDTO1).isNotEqualTo(machineModelDTO2);
        machineModelDTO2.setId(machineModelDTO1.getId());
        assertThat(machineModelDTO1).isEqualTo(machineModelDTO2);
        machineModelDTO2.setId(2L);
        assertThat(machineModelDTO1).isNotEqualTo(machineModelDTO2);
        machineModelDTO1.setId(null);
        assertThat(machineModelDTO1).isNotEqualTo(machineModelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(machineModelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(machineModelMapper.fromId(null)).isNull();
    }
}
