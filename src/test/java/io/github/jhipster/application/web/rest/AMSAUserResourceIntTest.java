package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.AMSAUser;
import io.github.jhipster.application.domain.User;
import io.github.jhipster.application.repository.AMSAUserRepository;
import io.github.jhipster.application.repository.search.AMSAUserSearchRepository;
import io.github.jhipster.application.service.AMSAUserService;
import io.github.jhipster.application.service.dto.AMSAUserDTO;
import io.github.jhipster.application.service.mapper.AMSAUserMapper;
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

import io.github.jhipster.application.domain.enumeration.AMSAuserType;
/**
 * Test class for the AMSAUserResource REST controller.
 *
 * @see AMSAUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class AMSAUserResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final AMSAuserType DEFAULT_USER_TYPE = AMSAuserType.CUSTOMER;
    private static final AMSAuserType UPDATED_USER_TYPE = AMSAuserType.ADMIN;

    @Autowired
    private AMSAUserRepository aMSAUserRepository;


    @Autowired
    private AMSAUserMapper aMSAUserMapper;
    

    @Autowired
    private AMSAUserService aMSAUserService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.AMSAUserSearchRepositoryMockConfiguration
     */
    @Autowired
    private AMSAUserSearchRepository mockAMSAUserSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAMSAUserMockMvc;

    private AMSAUser aMSAUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AMSAUserResource aMSAUserResource = new AMSAUserResource(aMSAUserService);
        this.restAMSAUserMockMvc = MockMvcBuilders.standaloneSetup(aMSAUserResource)
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
    public static AMSAUser createEntity(EntityManager em) {
        AMSAUser aMSAUser = new AMSAUser()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .company(DEFAULT_COMPANY)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .country(DEFAULT_COUNTRY)
            .userType(DEFAULT_USER_TYPE);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        aMSAUser.setJhuser(user);
        return aMSAUser;
    }

    @Before
    public void initTest() {
        aMSAUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createAMSAUser() throws Exception {
        int databaseSizeBeforeCreate = aMSAUserRepository.findAll().size();

        // Create the AMSAUser
        AMSAUserDTO aMSAUserDTO = aMSAUserMapper.toDto(aMSAUser);
        restAMSAUserMockMvc.perform(post("/api/amsa-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aMSAUserDTO)))
            .andExpect(status().isCreated());

        // Validate the AMSAUser in the database
        List<AMSAUser> aMSAUserList = aMSAUserRepository.findAll();
        assertThat(aMSAUserList).hasSize(databaseSizeBeforeCreate + 1);
        AMSAUser testAMSAUser = aMSAUserList.get(aMSAUserList.size() - 1);
        assertThat(testAMSAUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAMSAUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAMSAUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAMSAUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testAMSAUser.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testAMSAUser.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testAMSAUser.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testAMSAUser.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAMSAUser.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testAMSAUser.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAMSAUser.getUserType()).isEqualTo(DEFAULT_USER_TYPE);

        // Validate the AMSAUser in Elasticsearch
        verify(mockAMSAUserSearchRepository, times(1)).save(testAMSAUser);
    }

    @Test
    @Transactional
    public void createAMSAUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aMSAUserRepository.findAll().size();

        // Create the AMSAUser with an existing ID
        aMSAUser.setId(1L);
        AMSAUserDTO aMSAUserDTO = aMSAUserMapper.toDto(aMSAUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAMSAUserMockMvc.perform(post("/api/amsa-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aMSAUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AMSAUser in the database
        List<AMSAUser> aMSAUserList = aMSAUserRepository.findAll();
        assertThat(aMSAUserList).hasSize(databaseSizeBeforeCreate);

        // Validate the AMSAUser in Elasticsearch
        verify(mockAMSAUserSearchRepository, times(0)).save(aMSAUser);
    }

    @Test
    @Transactional
    public void getAllAMSAUsers() throws Exception {
        // Initialize the database
        aMSAUserRepository.saveAndFlush(aMSAUser);

        // Get all the aMSAUserList
        restAMSAUserMockMvc.perform(get("/api/amsa-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aMSAUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())));
    }
    

    @Test
    @Transactional
    public void getAMSAUser() throws Exception {
        // Initialize the database
        aMSAUserRepository.saveAndFlush(aMSAUser);

        // Get the aMSAUser
        restAMSAUserMockMvc.perform(get("/api/amsa-users/{id}", aMSAUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aMSAUser.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAMSAUser() throws Exception {
        // Get the aMSAUser
        restAMSAUserMockMvc.perform(get("/api/amsa-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAMSAUser() throws Exception {
        // Initialize the database
        aMSAUserRepository.saveAndFlush(aMSAUser);

        int databaseSizeBeforeUpdate = aMSAUserRepository.findAll().size();

        // Update the aMSAUser
        AMSAUser updatedAMSAUser = aMSAUserRepository.findById(aMSAUser.getId()).get();
        // Disconnect from session so that the updates on updatedAMSAUser are not directly saved in db
        em.detach(updatedAMSAUser);
        updatedAMSAUser
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .company(UPDATED_COMPANY)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .country(UPDATED_COUNTRY)
            .userType(UPDATED_USER_TYPE);
        AMSAUserDTO aMSAUserDTO = aMSAUserMapper.toDto(updatedAMSAUser);

        restAMSAUserMockMvc.perform(put("/api/amsa-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aMSAUserDTO)))
            .andExpect(status().isOk());

        // Validate the AMSAUser in the database
        List<AMSAUser> aMSAUserList = aMSAUserRepository.findAll();
        assertThat(aMSAUserList).hasSize(databaseSizeBeforeUpdate);
        AMSAUser testAMSAUser = aMSAUserList.get(aMSAUserList.size() - 1);
        assertThat(testAMSAUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAMSAUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAMSAUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAMSAUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAMSAUser.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testAMSAUser.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testAMSAUser.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testAMSAUser.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAMSAUser.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testAMSAUser.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAMSAUser.getUserType()).isEqualTo(UPDATED_USER_TYPE);

        // Validate the AMSAUser in Elasticsearch
        verify(mockAMSAUserSearchRepository, times(1)).save(testAMSAUser);
    }

    @Test
    @Transactional
    public void updateNonExistingAMSAUser() throws Exception {
        int databaseSizeBeforeUpdate = aMSAUserRepository.findAll().size();

        // Create the AMSAUser
        AMSAUserDTO aMSAUserDTO = aMSAUserMapper.toDto(aMSAUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAMSAUserMockMvc.perform(put("/api/amsa-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aMSAUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AMSAUser in the database
        List<AMSAUser> aMSAUserList = aMSAUserRepository.findAll();
        assertThat(aMSAUserList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AMSAUser in Elasticsearch
        verify(mockAMSAUserSearchRepository, times(0)).save(aMSAUser);
    }

    @Test
    @Transactional
    public void deleteAMSAUser() throws Exception {
        // Initialize the database
        aMSAUserRepository.saveAndFlush(aMSAUser);

        int databaseSizeBeforeDelete = aMSAUserRepository.findAll().size();

        // Get the aMSAUser
        restAMSAUserMockMvc.perform(delete("/api/amsa-users/{id}", aMSAUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AMSAUser> aMSAUserList = aMSAUserRepository.findAll();
        assertThat(aMSAUserList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AMSAUser in Elasticsearch
        verify(mockAMSAUserSearchRepository, times(1)).deleteById(aMSAUser.getId());
    }

    @Test
    @Transactional
    public void searchAMSAUser() throws Exception {
        // Initialize the database
        aMSAUserRepository.saveAndFlush(aMSAUser);
        when(mockAMSAUserSearchRepository.search(queryStringQuery("id:" + aMSAUser.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(aMSAUser), PageRequest.of(0, 1), 1));
        // Search the aMSAUser
        restAMSAUserMockMvc.perform(get("/api/_search/amsa-users?query=id:" + aMSAUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aMSAUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AMSAUser.class);
        AMSAUser aMSAUser1 = new AMSAUser();
        aMSAUser1.setId(1L);
        AMSAUser aMSAUser2 = new AMSAUser();
        aMSAUser2.setId(aMSAUser1.getId());
        assertThat(aMSAUser1).isEqualTo(aMSAUser2);
        aMSAUser2.setId(2L);
        assertThat(aMSAUser1).isNotEqualTo(aMSAUser2);
        aMSAUser1.setId(null);
        assertThat(aMSAUser1).isNotEqualTo(aMSAUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AMSAUserDTO.class);
        AMSAUserDTO aMSAUserDTO1 = new AMSAUserDTO();
        aMSAUserDTO1.setId(1L);
        AMSAUserDTO aMSAUserDTO2 = new AMSAUserDTO();
        assertThat(aMSAUserDTO1).isNotEqualTo(aMSAUserDTO2);
        aMSAUserDTO2.setId(aMSAUserDTO1.getId());
        assertThat(aMSAUserDTO1).isEqualTo(aMSAUserDTO2);
        aMSAUserDTO2.setId(2L);
        assertThat(aMSAUserDTO1).isNotEqualTo(aMSAUserDTO2);
        aMSAUserDTO1.setId(null);
        assertThat(aMSAUserDTO1).isNotEqualTo(aMSAUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(aMSAUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(aMSAUserMapper.fromId(null)).isNull();
    }
}
