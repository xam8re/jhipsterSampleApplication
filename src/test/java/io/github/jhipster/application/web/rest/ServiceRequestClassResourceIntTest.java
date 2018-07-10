package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.ServiceRequestClass;
import io.github.jhipster.application.repository.ServiceRequestClassRepository;
import io.github.jhipster.application.repository.search.ServiceRequestClassSearchRepository;
import io.github.jhipster.application.service.ServiceRequestClassService;
import io.github.jhipster.application.service.dto.ServiceRequestClassDTO;
import io.github.jhipster.application.service.mapper.ServiceRequestClassMapper;
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
 * Test class for the ServiceRequestClassResource REST controller.
 *
 * @see ServiceRequestClassResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ServiceRequestClassResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SHOW_ORIGINAL_CAD = false;
    private static final Boolean UPDATED_SHOW_ORIGINAL_CAD = true;

    private static final Boolean DEFAULT_SHOW_DESIGN_SPACE_CAD = false;
    private static final Boolean UPDATED_SHOW_DESIGN_SPACE_CAD = true;

    private static final Boolean DEFAULT_SHOW_NON_DESIGN_SPACE_CAD = false;
    private static final Boolean UPDATED_SHOW_NON_DESIGN_SPACE_CAD = true;

    private static final Boolean DEFAULT_SHOW_TECH_SPEC = false;
    private static final Boolean UPDATED_SHOW_TECH_SPEC = true;

    private static final Boolean DEFAULT_SHOW_MATERIAL = false;
    private static final Boolean UPDATED_SHOW_MATERIAL = true;

    private static final Boolean DEFAULT_SHOW_DIMENSIONS = false;
    private static final Boolean UPDATED_SHOW_DIMENSIONS = true;

    private static final Boolean DEFAULT_SHOW_PHOTO = false;
    private static final Boolean UPDATED_SHOW_PHOTO = true;

    @Autowired
    private ServiceRequestClassRepository serviceRequestClassRepository;


    @Autowired
    private ServiceRequestClassMapper serviceRequestClassMapper;
    

    @Autowired
    private ServiceRequestClassService serviceRequestClassService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ServiceRequestClassSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServiceRequestClassSearchRepository mockServiceRequestClassSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServiceRequestClassMockMvc;

    private ServiceRequestClass serviceRequestClass;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceRequestClassResource serviceRequestClassResource = new ServiceRequestClassResource(serviceRequestClassService);
        this.restServiceRequestClassMockMvc = MockMvcBuilders.standaloneSetup(serviceRequestClassResource)
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
    public static ServiceRequestClass createEntity(EntityManager em) {
        ServiceRequestClass serviceRequestClass = new ServiceRequestClass()
            .name(DEFAULT_NAME)
            .showOriginalCAD(DEFAULT_SHOW_ORIGINAL_CAD)
            .showDesignSpaceCAD(DEFAULT_SHOW_DESIGN_SPACE_CAD)
            .showNonDesignSpaceCAD(DEFAULT_SHOW_NON_DESIGN_SPACE_CAD)
            .showTechSpec(DEFAULT_SHOW_TECH_SPEC)
            .showMaterial(DEFAULT_SHOW_MATERIAL)
            .showDimensions(DEFAULT_SHOW_DIMENSIONS)
            .showPhoto(DEFAULT_SHOW_PHOTO);
        return serviceRequestClass;
    }

    @Before
    public void initTest() {
        serviceRequestClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceRequestClass() throws Exception {
        int databaseSizeBeforeCreate = serviceRequestClassRepository.findAll().size();

        // Create the ServiceRequestClass
        ServiceRequestClassDTO serviceRequestClassDTO = serviceRequestClassMapper.toDto(serviceRequestClass);
        restServiceRequestClassMockMvc.perform(post("/api/service-request-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestClassDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceRequestClass in the database
        List<ServiceRequestClass> serviceRequestClassList = serviceRequestClassRepository.findAll();
        assertThat(serviceRequestClassList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceRequestClass testServiceRequestClass = serviceRequestClassList.get(serviceRequestClassList.size() - 1);
        assertThat(testServiceRequestClass.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceRequestClass.isShowOriginalCAD()).isEqualTo(DEFAULT_SHOW_ORIGINAL_CAD);
        assertThat(testServiceRequestClass.isShowDesignSpaceCAD()).isEqualTo(DEFAULT_SHOW_DESIGN_SPACE_CAD);
        assertThat(testServiceRequestClass.isShowNonDesignSpaceCAD()).isEqualTo(DEFAULT_SHOW_NON_DESIGN_SPACE_CAD);
        assertThat(testServiceRequestClass.isShowTechSpec()).isEqualTo(DEFAULT_SHOW_TECH_SPEC);
        assertThat(testServiceRequestClass.isShowMaterial()).isEqualTo(DEFAULT_SHOW_MATERIAL);
        assertThat(testServiceRequestClass.isShowDimensions()).isEqualTo(DEFAULT_SHOW_DIMENSIONS);
        assertThat(testServiceRequestClass.isShowPhoto()).isEqualTo(DEFAULT_SHOW_PHOTO);

        // Validate the ServiceRequestClass in Elasticsearch
        verify(mockServiceRequestClassSearchRepository, times(1)).save(testServiceRequestClass);
    }

    @Test
    @Transactional
    public void createServiceRequestClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceRequestClassRepository.findAll().size();

        // Create the ServiceRequestClass with an existing ID
        serviceRequestClass.setId(1L);
        ServiceRequestClassDTO serviceRequestClassDTO = serviceRequestClassMapper.toDto(serviceRequestClass);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceRequestClassMockMvc.perform(post("/api/service-request-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequestClass in the database
        List<ServiceRequestClass> serviceRequestClassList = serviceRequestClassRepository.findAll();
        assertThat(serviceRequestClassList).hasSize(databaseSizeBeforeCreate);

        // Validate the ServiceRequestClass in Elasticsearch
        verify(mockServiceRequestClassSearchRepository, times(0)).save(serviceRequestClass);
    }

    @Test
    @Transactional
    public void getAllServiceRequestClasses() throws Exception {
        // Initialize the database
        serviceRequestClassRepository.saveAndFlush(serviceRequestClass);

        // Get all the serviceRequestClassList
        restServiceRequestClassMockMvc.perform(get("/api/service-request-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRequestClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].showOriginalCAD").value(hasItem(DEFAULT_SHOW_ORIGINAL_CAD.booleanValue())))
            .andExpect(jsonPath("$.[*].showDesignSpaceCAD").value(hasItem(DEFAULT_SHOW_DESIGN_SPACE_CAD.booleanValue())))
            .andExpect(jsonPath("$.[*].showNonDesignSpaceCAD").value(hasItem(DEFAULT_SHOW_NON_DESIGN_SPACE_CAD.booleanValue())))
            .andExpect(jsonPath("$.[*].showTechSpec").value(hasItem(DEFAULT_SHOW_TECH_SPEC.booleanValue())))
            .andExpect(jsonPath("$.[*].showMaterial").value(hasItem(DEFAULT_SHOW_MATERIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].showDimensions").value(hasItem(DEFAULT_SHOW_DIMENSIONS.booleanValue())))
            .andExpect(jsonPath("$.[*].showPhoto").value(hasItem(DEFAULT_SHOW_PHOTO.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getServiceRequestClass() throws Exception {
        // Initialize the database
        serviceRequestClassRepository.saveAndFlush(serviceRequestClass);

        // Get the serviceRequestClass
        restServiceRequestClassMockMvc.perform(get("/api/service-request-classes/{id}", serviceRequestClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceRequestClass.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.showOriginalCAD").value(DEFAULT_SHOW_ORIGINAL_CAD.booleanValue()))
            .andExpect(jsonPath("$.showDesignSpaceCAD").value(DEFAULT_SHOW_DESIGN_SPACE_CAD.booleanValue()))
            .andExpect(jsonPath("$.showNonDesignSpaceCAD").value(DEFAULT_SHOW_NON_DESIGN_SPACE_CAD.booleanValue()))
            .andExpect(jsonPath("$.showTechSpec").value(DEFAULT_SHOW_TECH_SPEC.booleanValue()))
            .andExpect(jsonPath("$.showMaterial").value(DEFAULT_SHOW_MATERIAL.booleanValue()))
            .andExpect(jsonPath("$.showDimensions").value(DEFAULT_SHOW_DIMENSIONS.booleanValue()))
            .andExpect(jsonPath("$.showPhoto").value(DEFAULT_SHOW_PHOTO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingServiceRequestClass() throws Exception {
        // Get the serviceRequestClass
        restServiceRequestClassMockMvc.perform(get("/api/service-request-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceRequestClass() throws Exception {
        // Initialize the database
        serviceRequestClassRepository.saveAndFlush(serviceRequestClass);

        int databaseSizeBeforeUpdate = serviceRequestClassRepository.findAll().size();

        // Update the serviceRequestClass
        ServiceRequestClass updatedServiceRequestClass = serviceRequestClassRepository.findById(serviceRequestClass.getId()).get();
        // Disconnect from session so that the updates on updatedServiceRequestClass are not directly saved in db
        em.detach(updatedServiceRequestClass);
        updatedServiceRequestClass
            .name(UPDATED_NAME)
            .showOriginalCAD(UPDATED_SHOW_ORIGINAL_CAD)
            .showDesignSpaceCAD(UPDATED_SHOW_DESIGN_SPACE_CAD)
            .showNonDesignSpaceCAD(UPDATED_SHOW_NON_DESIGN_SPACE_CAD)
            .showTechSpec(UPDATED_SHOW_TECH_SPEC)
            .showMaterial(UPDATED_SHOW_MATERIAL)
            .showDimensions(UPDATED_SHOW_DIMENSIONS)
            .showPhoto(UPDATED_SHOW_PHOTO);
        ServiceRequestClassDTO serviceRequestClassDTO = serviceRequestClassMapper.toDto(updatedServiceRequestClass);

        restServiceRequestClassMockMvc.perform(put("/api/service-request-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestClassDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceRequestClass in the database
        List<ServiceRequestClass> serviceRequestClassList = serviceRequestClassRepository.findAll();
        assertThat(serviceRequestClassList).hasSize(databaseSizeBeforeUpdate);
        ServiceRequestClass testServiceRequestClass = serviceRequestClassList.get(serviceRequestClassList.size() - 1);
        assertThat(testServiceRequestClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceRequestClass.isShowOriginalCAD()).isEqualTo(UPDATED_SHOW_ORIGINAL_CAD);
        assertThat(testServiceRequestClass.isShowDesignSpaceCAD()).isEqualTo(UPDATED_SHOW_DESIGN_SPACE_CAD);
        assertThat(testServiceRequestClass.isShowNonDesignSpaceCAD()).isEqualTo(UPDATED_SHOW_NON_DESIGN_SPACE_CAD);
        assertThat(testServiceRequestClass.isShowTechSpec()).isEqualTo(UPDATED_SHOW_TECH_SPEC);
        assertThat(testServiceRequestClass.isShowMaterial()).isEqualTo(UPDATED_SHOW_MATERIAL);
        assertThat(testServiceRequestClass.isShowDimensions()).isEqualTo(UPDATED_SHOW_DIMENSIONS);
        assertThat(testServiceRequestClass.isShowPhoto()).isEqualTo(UPDATED_SHOW_PHOTO);

        // Validate the ServiceRequestClass in Elasticsearch
        verify(mockServiceRequestClassSearchRepository, times(1)).save(testServiceRequestClass);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceRequestClass() throws Exception {
        int databaseSizeBeforeUpdate = serviceRequestClassRepository.findAll().size();

        // Create the ServiceRequestClass
        ServiceRequestClassDTO serviceRequestClassDTO = serviceRequestClassMapper.toDto(serviceRequestClass);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServiceRequestClassMockMvc.perform(put("/api/service-request-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequestClass in the database
        List<ServiceRequestClass> serviceRequestClassList = serviceRequestClassRepository.findAll();
        assertThat(serviceRequestClassList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ServiceRequestClass in Elasticsearch
        verify(mockServiceRequestClassSearchRepository, times(0)).save(serviceRequestClass);
    }

    @Test
    @Transactional
    public void deleteServiceRequestClass() throws Exception {
        // Initialize the database
        serviceRequestClassRepository.saveAndFlush(serviceRequestClass);

        int databaseSizeBeforeDelete = serviceRequestClassRepository.findAll().size();

        // Get the serviceRequestClass
        restServiceRequestClassMockMvc.perform(delete("/api/service-request-classes/{id}", serviceRequestClass.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceRequestClass> serviceRequestClassList = serviceRequestClassRepository.findAll();
        assertThat(serviceRequestClassList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ServiceRequestClass in Elasticsearch
        verify(mockServiceRequestClassSearchRepository, times(1)).deleteById(serviceRequestClass.getId());
    }

    @Test
    @Transactional
    public void searchServiceRequestClass() throws Exception {
        // Initialize the database
        serviceRequestClassRepository.saveAndFlush(serviceRequestClass);
        when(mockServiceRequestClassSearchRepository.search(queryStringQuery("id:" + serviceRequestClass.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(serviceRequestClass), PageRequest.of(0, 1), 1));
        // Search the serviceRequestClass
        restServiceRequestClassMockMvc.perform(get("/api/_search/service-request-classes?query=id:" + serviceRequestClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRequestClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].showOriginalCAD").value(hasItem(DEFAULT_SHOW_ORIGINAL_CAD.booleanValue())))
            .andExpect(jsonPath("$.[*].showDesignSpaceCAD").value(hasItem(DEFAULT_SHOW_DESIGN_SPACE_CAD.booleanValue())))
            .andExpect(jsonPath("$.[*].showNonDesignSpaceCAD").value(hasItem(DEFAULT_SHOW_NON_DESIGN_SPACE_CAD.booleanValue())))
            .andExpect(jsonPath("$.[*].showTechSpec").value(hasItem(DEFAULT_SHOW_TECH_SPEC.booleanValue())))
            .andExpect(jsonPath("$.[*].showMaterial").value(hasItem(DEFAULT_SHOW_MATERIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].showDimensions").value(hasItem(DEFAULT_SHOW_DIMENSIONS.booleanValue())))
            .andExpect(jsonPath("$.[*].showPhoto").value(hasItem(DEFAULT_SHOW_PHOTO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRequestClass.class);
        ServiceRequestClass serviceRequestClass1 = new ServiceRequestClass();
        serviceRequestClass1.setId(1L);
        ServiceRequestClass serviceRequestClass2 = new ServiceRequestClass();
        serviceRequestClass2.setId(serviceRequestClass1.getId());
        assertThat(serviceRequestClass1).isEqualTo(serviceRequestClass2);
        serviceRequestClass2.setId(2L);
        assertThat(serviceRequestClass1).isNotEqualTo(serviceRequestClass2);
        serviceRequestClass1.setId(null);
        assertThat(serviceRequestClass1).isNotEqualTo(serviceRequestClass2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRequestClassDTO.class);
        ServiceRequestClassDTO serviceRequestClassDTO1 = new ServiceRequestClassDTO();
        serviceRequestClassDTO1.setId(1L);
        ServiceRequestClassDTO serviceRequestClassDTO2 = new ServiceRequestClassDTO();
        assertThat(serviceRequestClassDTO1).isNotEqualTo(serviceRequestClassDTO2);
        serviceRequestClassDTO2.setId(serviceRequestClassDTO1.getId());
        assertThat(serviceRequestClassDTO1).isEqualTo(serviceRequestClassDTO2);
        serviceRequestClassDTO2.setId(2L);
        assertThat(serviceRequestClassDTO1).isNotEqualTo(serviceRequestClassDTO2);
        serviceRequestClassDTO1.setId(null);
        assertThat(serviceRequestClassDTO1).isNotEqualTo(serviceRequestClassDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceRequestClassMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceRequestClassMapper.fromId(null)).isNull();
    }
}
