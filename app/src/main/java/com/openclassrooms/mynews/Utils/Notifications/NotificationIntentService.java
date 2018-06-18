package com.openclassrooms.mynews.Utils.Notifications;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.openclassrooms.mynews.Controllers.Activities.WebViewActivity;
import com.openclassrooms.mynews.Models.Article;
import com.openclassrooms.mynews.Models.Articles;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.NyTimesStreams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * There, we specify onHandleIntent() which is responses on NotificationEventReceiver's intent we passed in startWakefulService method.
 * If it's Delete action - we can log it to our analytics, for example.
 * If it's Start notification intent - then by using NotificationCompat.Builder we're composing new notification and showing it by  NotificationManager.notify.
 * While composing notification, we are also setting pending intents for click and remove actions.
 */
public class NotificationIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";
    private Disposable disposable;
    private List<Article> articlesL;
    private SharedPreferences sharedPreferences;

    public NotificationIntentService() {
        super(NotificationIntentService.class.getSimpleName());
    }

    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                processStartNotification();
            }
            if (ACTION_DELETE.equals(action)) {
                processDeleteNotification(intent);
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void processStartNotification() {
        sharedPreferences = getBaseContext().getSharedPreferences("notifications", Context.MODE_PRIVATE);

        this.disposable = NyTimesStreams.streamFetchTopStories("home").subscribeWith(new DisposableObserver<Articles>() {
            @Override
            public void onNext(Articles articles) {
                articlesL = articles.getArticles();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formatR = new SimpleDateFormat("dd/MM/yyyy");
                Date currentTime = Calendar.getInstance().getTime();
                String dtCurrent = null;
                dtCurrent = formatR.format(currentTime);

                List<String> sectionList = new ArrayList<>();

                sectionList.add("arts");
                sectionList.add("politics");
                sectionList.add("sports");
                sectionList.add("travel");
                sectionList.add("business");
                sectionList.add("entrepreneurs");

                StringBuilder section = new StringBuilder();

                for (int i2 =0; i2<sectionList.size(); i2++){
                    if(sharedPreferences.getBoolean(sectionList.get(i2), false)){
                        section.append(sectionList.get(i2) + " ");
                    }
                }

                for(int i = 0; i<articlesL.size(); i++) {
                    String dtStart = articlesL.get(i).getPublishedDate();
                    dtStart = dtStart.replace("T"," ");
                    try {
                        Date date = format.parse(dtStart);
                        dtStart = formatR.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(dtStart.equals(dtCurrent) && section.toString().contains(articlesL.get(i).getSection().toLowerCase()) && articlesL.get(i).getTitle().toLowerCase().contains(sharedPreferences.getString("query", ""))) {
                        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), String.valueOf(NOTIFICATION_ID+i));
                        builder.setContentTitle(articlesL.get(i).getSection())
                                .setAutoCancel(true)
                                .setColor(getResources().getColor(R.color.bpWhite))
                                .setContentText(articlesL.get(i).getTitle())
                                .setSmallIcon(R.drawable.ic_nytimes_logo);

                        NotificationManager mNotificationManager =
                                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                        NotificationChannel channel = new NotificationChannel(String.valueOf(NOTIFICATION_ID+i),
                                "Channel human readable title",
                                NotificationManager.IMPORTANCE_DEFAULT);
                        mNotificationManager.createNotificationChannel(channel);

                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                                NOTIFICATION_ID+i,
                                new Intent(getApplicationContext(), WebViewActivity.class),
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(pendingIntent);
                        builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(getApplicationContext()));

                        final NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(NOTIFICATION_ID+i, builder.build());
                    }
                }
            }
        });


    }
}
