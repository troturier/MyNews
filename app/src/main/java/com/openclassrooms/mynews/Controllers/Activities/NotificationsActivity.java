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
import android.widget.CompoundButton;
import android.widget.Switch;

import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.Notifications.NotificationEventReceiver;

import java.util.ArrayList;
import java.util.List;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notifications");

        sharedPreferences = getBaseContext().getSharedPreferences("notifications", Context.MODE_PRIVATE);

        notifications_switch = findViewById(R.id.enable_notifications_switch);

        TextInputEditText queryTerms = findViewById(R.id.search_query_term_input);

        if (sharedPreferences.contains("query")){
            queryTerms.setText(sharedPreferences.getString("query", ""));
        }

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

        CheckBox cb_arts = findViewById(R.id.search_cb_arts);
        CheckBox cb_politics = findViewById(R.id.search_cb_politics);
        CheckBox cb_business = findViewById(R.id.search_cb_business);
        CheckBox cb_sports = findViewById(R.id.search_cb_sports);
        CheckBox cb_entrepreneurs = findViewById(R.id.search_cb_entrepreneurs);
        CheckBox cb_travel = findViewById(R.id.search_cb_travel);
        List<CheckBox> cb = new ArrayList<CheckBox>();

        cb.add(cb_arts);
        cb.add(cb_politics);
        cb.add(cb_business);
        cb.add(cb_sports);
        cb.add(cb_entrepreneurs);
        cb.add(cb_travel);


        for(int i=0; i<cb.size(); i++){
            int finalI = i;
            cb.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    sharedPreferences.edit()
                            .putBoolean(cb.get(finalI).getText().toString().toLowerCase(), cb_arts.isChecked())
                            .apply();
                }
            });
            if (sharedPreferences.contains(cb.get(finalI).getText().toString().toLowerCase())){
                cb.get(finalI).setChecked(sharedPreferences.getBoolean(cb.get(finalI).getText().toString().toLowerCase(), false));
            }
        }

        if (sharedPreferences.contains("notifications")){
            notifications_switch.setChecked(sharedPreferences.getBoolean("notifications", false));
        }

        notifications_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onSendNotificationsSwitchEnable(compoundButton);
                sharedPreferences.edit()
                    .putBoolean("notifications", notifications_switch.isChecked())
                    .apply();
            }
        });
    }

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
