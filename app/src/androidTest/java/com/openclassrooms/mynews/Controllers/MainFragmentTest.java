package com.openclassrooms.mynews.Controllers;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.openclassrooms.mynews.Models.Article;
import com.openclassrooms.mynews.Models.Articles;
import com.openclassrooms.mynews.Utils.NyTimesStreamsTest;
import com.openclassrooms.mynews.Utils.RestServiceTestHelper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainFragmentTest extends InstrumentationTestCase {

    @ClassRule
    public static MockWebServer server;

    @BeforeClass
    public static void setUpClass() throws Exception {
        server = new MockWebServer();
        server.start();
    }

    @Before
    public void setUp() throws Exception{
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void fetchMostPopularTest() throws Exception {
        String fileName = "mostPopular_200_ok_response.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Observable<Articles> observableArticles = NyTimesStreamsTest.streamFetchMostPopular();
        TestObserver<Articles> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        List<Article> articles = testObserver.values().get(0).getArticles();

        assertThat("The result list is not empty", !articles.isEmpty());

    }

    @Test
    public void fetchTopStoriesTest() throws Exception {
        String fileName = "topStories_200_ok_response.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Observable<Articles> observableArticles = NyTimesStreamsTest.streamFetchTopStories();
        TestObserver<Articles> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        List<Article> articles = testObserver.values().get(0).getArticles();

        assertThat("The result list is not empty", !articles.isEmpty());
    }
}
