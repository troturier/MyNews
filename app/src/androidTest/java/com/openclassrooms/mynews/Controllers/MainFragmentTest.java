package com.openclassrooms.mynews.Controllers;

import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.mynews.Models.Article;
import com.openclassrooms.mynews.Models.Articles;
import com.openclassrooms.mynews.Utils.NyTimesStreams;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainFragmentTest {

    @Test
    public void fetchTopStoriesTest() throws Exception {
        Observable<Articles> observableArticles = NyTimesStreams.streamFetchTopStories();
        TestObserver<Articles> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        List<Article> articles = testObserver.values().get(0).getArticles();

        assertThat("The result list is not empty", !articles.isEmpty());
    }

    @Test
    public void fetchMostPopularTest() throws Exception {
        Observable<Articles> observableArticles = NyTimesStreams.streamFetchMostPopular();
        TestObserver<Articles> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        List<Article> articles = testObserver.values().get(0).getArticles();

        assertThat("The result list is not empty", !articles.isEmpty());
    }
}
