package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Tweet;
import io.github.jhipster.application.repository.TweetRepository;
import io.github.jhipster.application.service.TweetService;
import io.github.jhipster.application.repository.search.TweetSearchRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.Sentiment;
/**
 * Test class for the TweetResource REST controller.
 *
 * @see TweetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TweetResourceIntTest {

    private static final String DEFAULT_TWEET_ID = "AAAAAAAAAA";
    private static final String UPDATED_TWEET_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_TWEET_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TWEET_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TWEET_GEO = "AAAAAAAAAA";
    private static final String UPDATED_TWEET_GEO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final Sentiment DEFAULT_SENTIMENT = Sentiment.POSITIVO;
    private static final Sentiment UPDATED_SENTIMENT = Sentiment.NEGATIVO;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private TweetSearchRepository tweetSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTweetMockMvc;

    private Tweet tweet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TweetResource tweetResource = new TweetResource(tweetService);
        this.restTweetMockMvc = MockMvcBuilders.standaloneSetup(tweetResource)
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
    public static Tweet createEntity(EntityManager em) {
        Tweet tweet = new Tweet()
            .tweetId(DEFAULT_TWEET_ID)
            .tweetData(DEFAULT_TWEET_DATA)
            .tweetGeo(DEFAULT_TWEET_GEO)
            .content(DEFAULT_CONTENT)
            .userid(DEFAULT_USERID)
            .sentiment(DEFAULT_SENTIMENT);
        return tweet;
    }

    @Before
    public void initTest() {
        tweetSearchRepository.deleteAll();
        tweet = createEntity(em);
    }

    @Test
    @Transactional
    public void createTweet() throws Exception {
        int databaseSizeBeforeCreate = tweetRepository.findAll().size();

        // Create the Tweet
        restTweetMockMvc.perform(post("/api/tweets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweet)))
            .andExpect(status().isCreated());

        // Validate the Tweet in the database
        List<Tweet> tweetList = tweetRepository.findAll();
        assertThat(tweetList).hasSize(databaseSizeBeforeCreate + 1);
        Tweet testTweet = tweetList.get(tweetList.size() - 1);
        assertThat(testTweet.getTweetId()).isEqualTo(DEFAULT_TWEET_ID);
        assertThat(testTweet.getTweetData()).isEqualTo(DEFAULT_TWEET_DATA);
        assertThat(testTweet.getTweetGeo()).isEqualTo(DEFAULT_TWEET_GEO);
        assertThat(testTweet.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testTweet.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testTweet.getSentiment()).isEqualTo(DEFAULT_SENTIMENT);

        // Validate the Tweet in Elasticsearch
        Tweet tweetEs = tweetSearchRepository.findOne(testTweet.getId());
        assertThat(tweetEs).isEqualToIgnoringGivenFields(testTweet);
    }

    @Test
    @Transactional
    public void createTweetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tweetRepository.findAll().size();

        // Create the Tweet with an existing ID
        tweet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTweetMockMvc.perform(post("/api/tweets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweet)))
            .andExpect(status().isBadRequest());

        // Validate the Tweet in the database
        List<Tweet> tweetList = tweetRepository.findAll();
        assertThat(tweetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTweetIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tweetRepository.findAll().size();
        // set the field null
        tweet.setTweetId(null);

        // Create the Tweet, which fails.

        restTweetMockMvc.perform(post("/api/tweets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweet)))
            .andExpect(status().isBadRequest());

        List<Tweet> tweetList = tweetRepository.findAll();
        assertThat(tweetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = tweetRepository.findAll().size();
        // set the field null
        tweet.setContent(null);

        // Create the Tweet, which fails.

        restTweetMockMvc.perform(post("/api/tweets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweet)))
            .andExpect(status().isBadRequest());

        List<Tweet> tweetList = tweetRepository.findAll();
        assertThat(tweetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUseridIsRequired() throws Exception {
        int databaseSizeBeforeTest = tweetRepository.findAll().size();
        // set the field null
        tweet.setUserid(null);

        // Create the Tweet, which fails.

        restTweetMockMvc.perform(post("/api/tweets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweet)))
            .andExpect(status().isBadRequest());

        List<Tweet> tweetList = tweetRepository.findAll();
        assertThat(tweetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTweets() throws Exception {
        // Initialize the database
        tweetRepository.saveAndFlush(tweet);

        // Get all the tweetList
        restTweetMockMvc.perform(get("/api/tweets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tweet.getId().intValue())))
            .andExpect(jsonPath("$.[*].tweetId").value(hasItem(DEFAULT_TWEET_ID.toString())))
            .andExpect(jsonPath("$.[*].tweetData").value(hasItem(DEFAULT_TWEET_DATA.toString())))
            .andExpect(jsonPath("$.[*].tweetGeo").value(hasItem(DEFAULT_TWEET_GEO.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.toString())))
            .andExpect(jsonPath("$.[*].sentiment").value(hasItem(DEFAULT_SENTIMENT.toString())));
    }

    @Test
    @Transactional
    public void getTweet() throws Exception {
        // Initialize the database
        tweetRepository.saveAndFlush(tweet);

        // Get the tweet
        restTweetMockMvc.perform(get("/api/tweets/{id}", tweet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tweet.getId().intValue()))
            .andExpect(jsonPath("$.tweetId").value(DEFAULT_TWEET_ID.toString()))
            .andExpect(jsonPath("$.tweetData").value(DEFAULT_TWEET_DATA.toString()))
            .andExpect(jsonPath("$.tweetGeo").value(DEFAULT_TWEET_GEO.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.toString()))
            .andExpect(jsonPath("$.sentiment").value(DEFAULT_SENTIMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTweet() throws Exception {
        // Get the tweet
        restTweetMockMvc.perform(get("/api/tweets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTweet() throws Exception {
        // Initialize the database
        tweetService.save(tweet);

        int databaseSizeBeforeUpdate = tweetRepository.findAll().size();

        // Update the tweet
        Tweet updatedTweet = tweetRepository.findOne(tweet.getId());
        // Disconnect from session so that the updates on updatedTweet are not directly saved in db
        em.detach(updatedTweet);
        updatedTweet
            .tweetId(UPDATED_TWEET_ID)
            .tweetData(UPDATED_TWEET_DATA)
            .tweetGeo(UPDATED_TWEET_GEO)
            .content(UPDATED_CONTENT)
            .userid(UPDATED_USERID)
            .sentiment(UPDATED_SENTIMENT);

        restTweetMockMvc.perform(put("/api/tweets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTweet)))
            .andExpect(status().isOk());

        // Validate the Tweet in the database
        List<Tweet> tweetList = tweetRepository.findAll();
        assertThat(tweetList).hasSize(databaseSizeBeforeUpdate);
        Tweet testTweet = tweetList.get(tweetList.size() - 1);
        assertThat(testTweet.getTweetId()).isEqualTo(UPDATED_TWEET_ID);
        assertThat(testTweet.getTweetData()).isEqualTo(UPDATED_TWEET_DATA);
        assertThat(testTweet.getTweetGeo()).isEqualTo(UPDATED_TWEET_GEO);
        assertThat(testTweet.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTweet.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testTweet.getSentiment()).isEqualTo(UPDATED_SENTIMENT);

        // Validate the Tweet in Elasticsearch
        Tweet tweetEs = tweetSearchRepository.findOne(testTweet.getId());
        assertThat(tweetEs).isEqualToIgnoringGivenFields(testTweet);
    }

    @Test
    @Transactional
    public void updateNonExistingTweet() throws Exception {
        int databaseSizeBeforeUpdate = tweetRepository.findAll().size();

        // Create the Tweet

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTweetMockMvc.perform(put("/api/tweets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweet)))
            .andExpect(status().isCreated());

        // Validate the Tweet in the database
        List<Tweet> tweetList = tweetRepository.findAll();
        assertThat(tweetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTweet() throws Exception {
        // Initialize the database
        tweetService.save(tweet);

        int databaseSizeBeforeDelete = tweetRepository.findAll().size();

        // Get the tweet
        restTweetMockMvc.perform(delete("/api/tweets/{id}", tweet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tweetExistsInEs = tweetSearchRepository.exists(tweet.getId());
        assertThat(tweetExistsInEs).isFalse();

        // Validate the database is empty
        List<Tweet> tweetList = tweetRepository.findAll();
        assertThat(tweetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTweet() throws Exception {
        // Initialize the database
        tweetService.save(tweet);

        // Search the tweet
        restTweetMockMvc.perform(get("/api/_search/tweets?query=id:" + tweet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tweet.getId().intValue())))
            .andExpect(jsonPath("$.[*].tweetId").value(hasItem(DEFAULT_TWEET_ID.toString())))
            .andExpect(jsonPath("$.[*].tweetData").value(hasItem(DEFAULT_TWEET_DATA.toString())))
            .andExpect(jsonPath("$.[*].tweetGeo").value(hasItem(DEFAULT_TWEET_GEO.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.toString())))
            .andExpect(jsonPath("$.[*].sentiment").value(hasItem(DEFAULT_SENTIMENT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tweet.class);
        Tweet tweet1 = new Tweet();
        tweet1.setId(1L);
        Tweet tweet2 = new Tweet();
        tweet2.setId(tweet1.getId());
        assertThat(tweet1).isEqualTo(tweet2);
        tweet2.setId(2L);
        assertThat(tweet1).isNotEqualTo(tweet2);
        tweet1.setId(null);
        assertThat(tweet1).isNotEqualTo(tweet2);
    }
}
