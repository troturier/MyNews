package com.openclassrooms.mynews.Controllers.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.Notifications.NotificationEventReceiver;

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
