package com.openclassrooms.mynews.Controllers.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.mynews.Models.Result;
import com.openclassrooms.mynews.Models.TopStories;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.TopStoriesCalls;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TopStoriesCalls.Callbacks  {

    private final static String TAG = MainActivity.class.getSimpleName();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.hello_world);

        this.executeHttpRequestWithRetrofit();
    }

    // ------------------------------
    //  HTTP REQUEST (Retrofit Way)
    // ------------------------------

    private void executeHttpRequestWithRetrofit(){
        this.updateUIWhenStartingHTTPRequest();
        TopStoriesCalls.fetchTopStories(this);
    }

    @Override
    public void onResponse(@Nullable TopStories stories) {
        if (stories != null) this.updateUIWithListOfStories(stories.getResults());
        else{
            Toast.makeText(this, "Server returned an error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure() {
        this.updateUIWhenStopingHTTPRequest("An error happened !");
    }

    // ------------------
    //  UPDATE UI
    // ------------------

    private void updateUIWhenStartingHTTPRequest(){
        this.textView.setText("Downloading...");
    }

    private void updateUIWhenStopingHTTPRequest(String response){
        this.textView.setText(response);
    }

    // 3 - Update UI showing only name of users
    private void updateUIWithListOfStories(List<Result> stories){
        StringBuilder stringBuilder = new StringBuilder();
        for (Result story : stories){
            stringBuilder.append("-"+story.getTitle()+"\n\n");
        }
        updateUIWhenStopingHTTPRequest(stringBuilder.toString());
    }

}
