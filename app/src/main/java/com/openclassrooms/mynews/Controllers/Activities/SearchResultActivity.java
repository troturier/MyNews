package com.openclassrooms.mynews.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.openclassrooms.mynews.Controllers.Fragments.SearchFragment;
import com.openclassrooms.mynews.R;

public class SearchResultActivity extends AppCompatActivity {

    private SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_frame_layout);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        // Setting up the return button and the activity title displayed in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieving extras from the intent
        Intent intent = getIntent();
        String query = intent.getStringExtra("SearchQuery");
        if(TextUtils.isEmpty(query)) {
            getSupportActionBar().setTitle("Search Results");
        }
        else{
            getSupportActionBar().setTitle("Results for \"" + query + '"');
        }

        if(savedInstanceState == null) {
            this.configureAndShowSearchFragment(query);
        }
        else{
            searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_frame_layout);
        }

    }

    /**
     * Function required for the back button to return to the previous activity
     * @return True if succeeded
     */
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void configureAndShowSearchFragment(String query){
        if(searchFragment == null ) {
            searchFragment = new SearchFragment();
            Bundle bundle = new Bundle();
            bundle.putString("query", query);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_frame_layout, searchFragment)
                    .commit();
        }
    }
}