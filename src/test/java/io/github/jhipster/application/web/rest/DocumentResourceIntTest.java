package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Document;
import io.github.jhipster.application.repository.DocumentRepository;
import io.github.jhipster.application.repository.search.DocumentSearchRepository;
import io.github.jhipster.application.service.DocumentService;
import io.github.jhipster.application.service.dto.DocumentDTO;
import io.github.jhipster.application.service.mapper.DocumentMapper;
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
 * Test class for the DocumentResource REST controller.
 *
 * @see DocumentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DocumentResourceIntTest {

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EXT = "AAAAAAAAAA";
    private static final String UPDATED_EXT = "BBBBBBBBBB";

    @Autowired
    private DocumentRepository documentRepository;


    @Autowired
    private DocumentMapper documentMapper;
    

    @Autowired
    private DocumentService documentService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.DocumentSearchRepositoryMockConfiguration
     */
    @Autowired
    private DocumentSearchRepository mockDocumentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDocumentMockMvc;

    private Document document;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentResource documentResource = new DocumentResource(documentService);
        this.restDocumentMockMvc = MockMvcBuilders.standaloneSetup(documentResource)
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
    public static Document createEntity(EntityManager em) {
        Document document = new Document()
            .path(DEFAULT_PATH)
            .type(DEFAULT_TYPE)
            .ext(DEFAULT_EXT);
        return document;
    }

    @Before
    public void initTest() {
        document = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocument() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();

        // Create the Document
        DocumentDTO documentDTO = documentMapper.toDto(document);
        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isCreated());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate + 1);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testDocument.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDocument.getExt()).isEqualTo(DEFAULT_EXT);

        // Validate the Document in Elasticsearch
        verify(mockDocumentSearchRepository, times(1)).save(testDocument);
    }

    @Test
    @Transactional
    public void createDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();

        // Create the Document with an existing ID
        document.setId(1L);
        DocumentDTO documentDTO = documentMapper.toDto(document);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate);

        // Validate the Document in Elasticsearch
        verify(mockDocumentSearchRepository, times(0)).save(document);
    }

    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRepository.findAll().size();
        // set the field null
        document.setPath(null);

        // Create the Document, which fails.
        DocumentDTO documentDTO = documentMapper.toDto(document);

        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isBadRequest());

        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocuments() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList
        restDocumentMockMvc.perform(get("/api/documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ext").value(hasItem(DEFAULT_EXT.toString())));
    }
    

    @Test
    @Transactional
    public void getDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get the document
        restDocumentMockMvc.perform(get("/api/documents/{id}", document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(document.getId().intValue()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.ext").value(DEFAULT_EXT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDocument() throws Exception {
        // Get the document
        restDocumentMockMvc.perform(get("/api/documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document
        Document updatedDocument = documentRepository.findById(document.getId()).get();
        // Disconnect from session so that the updates on updatedDocument are not directly saved in db
        em.detach(updatedDocument);
        updatedDocument
            .path(UPDATED_PATH)
            .type(UPDATED_TYPE)
            .ext(UPDATED_EXT);
        DocumentDTO documentDTO = documentMapper.toDto(updatedDocument);

        restDocumentMockMvc.perform(put("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testDocument.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDocument.getExt()).isEqualTo(UPDATED_EXT);

        // Validate the Document in Elasticsearch
        verify(mockDocumentSearchRepository, times(1)).save(testDocument);
    }

    @Test
    @Transactional
    public void updateNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Create the Document
        DocumentDTO documentDTO = documentMapper.toDto(document);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDocumentMockMvc.perform(put("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Document in Elasticsearch
        verify(mockDocumentSearchRepository, times(0)).save(document);
    }

    @Test
    @Transactional
    public void deleteDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeDelete = documentRepository.findAll().size();

        // Get the document
        restDocumentMockMvc.perform(delete("/api/documents/{id}", document.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Document in Elasticsearch
        verify(mockDocumentSearchRepository, times(1)).deleteById(document.getId());
    }

    @Test
    @Transactional
    public void searchDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);
        when(mockDocumentSearchRepository.search(queryStringQuery("id:" + document.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(document), PageRequest.of(0, 1), 1));
        // Search the document
        restDocumentMockMvc.perform(get("/api/_search/documents?query=id:" + document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ext").value(hasItem(DEFAULT_EXT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Document.class);
        Document document1 = new Document();
        document1.setId(1L);
        Document document2 = new Document();
        document2.setId(document1.getId());
        assertThat(document1).isEqualTo(document2);
        document2.setId(2L);
        assertThat(document1).isNotEqualTo(document2);
        document1.setId(null);
        assertThat(document1).isNotEqualTo(document2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentDTO.class);
        DocumentDTO documentDTO1 = new DocumentDTO();
        documentDTO1.setId(1L);
        DocumentDTO documentDTO2 = new DocumentDTO();
        assertThat(documentDTO1).isNotEqualTo(documentDTO2);
        documentDTO2.setId(documentDTO1.getId());
        assertThat(documentDTO1).isEqualTo(documentDTO2);
        documentDTO2.setId(2L);
        assertThat(documentDTO1).isNotEqualTo(documentDTO2);
        documentDTO1.setId(null);
        assertThat(documentDTO1).isNotEqualTo(documentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(documentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(documentMapper.fromId(null)).isNull();
    }
}
