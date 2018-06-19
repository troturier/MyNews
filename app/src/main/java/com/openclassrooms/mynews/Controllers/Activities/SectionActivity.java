package com.openclassrooms.mynews.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.openclassrooms.mynews.Controllers.Fragments.SectionFragment;
import com.openclassrooms.mynews.R;

public class SectionActivity extends AppCompatActivity {
    private SectionFragment sectionFragment = (SectionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_section_frame_layout);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        // Setting up the return button and the activity title displayed in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieving extras from the intent
        Intent intent = getIntent();
        String section = intent.getStringExtra("section");
        getSupportActionBar().setTitle(section);

        if(savedInstanceState == null) {
            this.configureAndShowSearchFragment(section);
        }
        else{
            sectionFragment = (SectionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_frame_layout);
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

    public void configureAndShowSearchFragment(String section){
        if(sectionFragment == null ) {
            sectionFragment = new SectionFragment();
            Bundle bundle = new Bundle();
            bundle.putString("section", section);
            sectionFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_section_frame_layout, sectionFragment)
                    .commit();
        }
    }
}
