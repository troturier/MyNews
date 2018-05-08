package com.openclassrooms.mynews.Controllers.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.openclassrooms.mynews.Controllers.Fragments.MainFragment;
import com.openclassrooms.mynews.R;

public class MainActivity extends AppCompatActivity  {

    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureAndShowMainFragment();
    }

    // -------------------
    // CONFIGURATION
    // -------------------

    private void configureAndShowMainFragment(){

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_frame_layout, mainFragment)
                    .commit();
        }
    }

}
