package com.openclassrooms.mynews.Controllers.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.MyApplication;
import com.openclassrooms.mynews.Utils.Notifications.NotificationEventReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Activity used for notifications
 */
public class NotificationsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Switch notifications_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Setting up the return button and the activity title displayed in the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notifications");

        sharedPreferences = getBaseContext().getSharedPreferences("notifications", Context.MODE_PRIVATE);

        notifications_switch = findViewById(R.id.enable_notifications_switch);

        TextInputEditText queryTerms = findViewById(R.id.search_query_term_input);

        // Changing the content of the search bar according to the sharedPreferences
        if (sharedPreferences.contains("query")){
            queryTerms.setText(sharedPreferences.getString("query", ""));
        }

        // Save the contents of the search bar each time it is modified
        queryTerms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sharedPreferences.edit()
                        .putString("query", queryTerms.getText().toString())
                        .apply();
            }
        });

        // Fetching all checkboxes
        CheckBox cb_arts = findViewById(R.id.search_cb_arts);
        CheckBox cb_politics = findViewById(R.id.search_cb_politics);
        CheckBox cb_business = findViewById(R.id.search_cb_business);
        CheckBox cb_sports = findViewById(R.id.search_cb_sports);
        CheckBox cb_entrepreneurs = findViewById(R.id.search_cb_entrepreneurs);
        CheckBox cb_travel = findViewById(R.id.search_cb_travel);

        // Creating a list of checkboxes that will contain all those that have been retrieved
        List<CheckBox> cb = new ArrayList<>();
        cb.add(cb_arts);
        cb.add(cb_politics);
        cb.add(cb_business);
        cb.add(cb_sports);
        cb.add(cb_entrepreneurs);
        cb.add(cb_travel);

        /*
          Support for checkboxes
          Saves the status of each checkbox in the sharedPreferences
         */
        for(int i=0; i<cb.size(); i++){
            int finalI = i;
            cb.get(i).setOnCheckedChangeListener((compoundButton, b) -> sharedPreferences.edit()
                    .putBoolean(cb.get(finalI).getText().toString().toLowerCase(), cb.get(finalI).isChecked())
                    .apply());
            // Changing the state of each checkbox according to the sharedPreferences
            if (sharedPreferences.contains(cb.get(finalI).getText().toString().toLowerCase())){
                cb.get(finalI).setChecked(sharedPreferences.getBoolean(cb.get(finalI).getText().toString().toLowerCase(), false));
            }
        }

        // Changing the position of the switch button according to the sharedPreferences
        if (sharedPreferences.contains("notifications")){
            notifications_switch.setChecked(sharedPreferences.getBoolean("notifications", false));
        }

        // Switch button
        notifications_switch.setOnCheckedChangeListener((compoundButton, b) -> {
            // Check if at least one checkbox is checked
            Boolean oneCbChecked = false;
            for(int i=0; i<cb.size(); i++) {
                if (cb.get(i).isChecked())
                    oneCbChecked = true;
            }
            // If not, will display a Toast message to the user
            if(notifications_switch.isChecked() && !oneCbChecked) {
                Toast.makeText(MyApplication.getAppContext(), "Please specify at least one category to enable notifications", Toast.LENGTH_LONG).show();
                notifications_switch.setChecked(false);
            }
            // If true, saves the switch button position in the sharedPreferences
            else {
                onSendNotificationsSwitchEnable(compoundButton);
                sharedPreferences.edit()
                        .putBoolean("notifications", notifications_switch.isChecked())
                        .apply();
            }
        });
    }

    /**
     * Enabling or not notifications features
     * @param view View object
     */
    public void onSendNotificationsSwitchEnable(View view){
        if (notifications_switch.isChecked())
            NotificationEventReceiver.setupAlarm(getApplicationContext());
        else
            NotificationEventReceiver.deleteAlarm(getApplicationContext());
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
}
