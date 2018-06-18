package com.openclassrooms.mynews.Utils.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast receiver for BOOT_COMPLETED, TIMEZONE_CHANGED, and TIME_SET events
 * to re-setup the AlarmManager, once device has been rebooted or timezone has changed (For example,
 * user flown from USA to Europe and you don't want notification to pop up in the middle of the night,
 * but was sticky to the local time).
 */
public final class NotificationServiceStarterReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationEventReceiver.setupAlarm(context);
    }
}
