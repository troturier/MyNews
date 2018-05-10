package com.openclassrooms.mynews.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.openclassrooms.mynews.Controllers.Fragments.MainFragment;
import com.openclassrooms.mynews.Controllers.Fragments.SampleFragmentPagerAdapter;
import com.openclassrooms.mynews.R;

/**
 * Class for the Main Activity
 * Manages toolbar and MainFragment configuration
 */
public class MainActivity extends AppCompatActivity  {

    private final static String TAG = MainActivity.class.getSimpleName();
    private MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
    public static int tabPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureAndShowMainFragment();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                viewPager.setCurrentItem(tab.getPosition());
                tabPos = tab.getPosition();
                mainFragment.executeHttpRequestWithRetrofit();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mainFragment.executeHttpRequestWithRetrofit();
            }
        });
    }

    /**
     * Inflate the menu : adds items to the action bar.
     * @param menu Menu item
     * @return True if succeeded
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * When the user selects an item from the options menu (including action items in the app bar),
     * the system calls the activity's onOptionsItemSelected() method.
     * This method passes the MenuItem selected.
     * @param item MenuItem
     * @return Boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mSearch:
                Intent intentSearch = new Intent(this, SearchActivity.class);
                startActivity(intentSearch);

            case R.id.mNotifications:
                Intent intentNotifications = new Intent(this, NotificationsActivity.class);
                startActivity(intentNotifications);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // -------------------
    // CONFIGURATION
    // -------------------

    /**
     * Configure and show the MainFragment
     */
    private void configureAndShowMainFragment(){

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_frame_layout, mainFragment)
                    .commit();
        }
    }

}
