package com.openclassrooms.mynews.Controllers;

import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.mynews.Models.Result;
import com.openclassrooms.mynews.Models.TopStories;
import com.openclassrooms.mynews.Utils.TopStoriesStreams;

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
        Observable<TopStories> observableTopStories = TopStoriesStreams.streamFetchTopStories();
        TestObserver<TopStories> testObserver = new TestObserver<>();

        observableTopStories.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        List<Result> results = testObserver.values().get(0).getResults();

        assertThat("The result list is not empty", !results.isEmpty());
    }
}
