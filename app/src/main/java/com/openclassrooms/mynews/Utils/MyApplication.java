package com.openclassrooms.mynews.Utils;

import android.app.Application;
import android.content.Context;

/**
 * Class used to retrieve the context of the application anywhere it's needed
 */
public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
