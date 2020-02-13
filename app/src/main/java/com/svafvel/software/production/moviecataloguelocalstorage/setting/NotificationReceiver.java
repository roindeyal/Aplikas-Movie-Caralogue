package com.svafvel.software.production.moviecataloguelocalstorage.setting;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.svafvel.software.production.moviecataloguelocalstorage.MainActivity;
import com.svafvel.software.production.moviecataloguelocalstorage.R;
import com.svafvel.software.production.moviecataloguelocalstorage.reminder.movie.Movie;
import com.svafvel.software.production.moviecataloguelocalstorage.reminder.movie.MovieResult;
import com.svafvel.software.production.moviecataloguelocalstorage.setting.rest.ApiClient;
import com.svafvel.software.production.moviecataloguelocalstorage.setting.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationReceiver extends BroadcastReceiver {
    private static final String EXTRA_TYPE = "type";
    private static final String TYPE_DAILY = "daily_reminder";
    private static final String TYPE_RELEASE = "release_reminder";
    private static final int ID_DAILY_REMINDER = 1000;
    private static final int ID_RELEASE_TODAY = 1001;

    // Api Key
    private String API_KEY = "b1ca6453a5660511fc0d8eb70aa35969";

    private Context context;

    public NotificationReceiver() {

    }

    public NotificationReceiver(Context context) {
        this.context = context;

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        if (type.equals(TYPE_DAILY)) {
            showDailyReminder(context);
        }else {
            getReleaseToday(context);

        }
    }

    private Calendar getReminderTime(String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, type.equals(TYPE_DAILY) ? 7 : 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        return calendar;
    }

    private Intent getReminderIntent(String type) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);
        return intent;
    }

    public void setReleaseTodayReminder() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_TODAY, getReminderIntent(TYPE_RELEASE), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_RELEASE).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    public void setDailyReminder() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, getReminderIntent(TYPE_DAILY), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_DAILY).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    public void getReleaseToday(final Context context) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String dateNow = simpleDateFormat.format(date);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> movieCall = apiInterface.getReleaseToday(API_KEY, context.getString(R.string.localization), dateNow, dateNow);
        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.isSuccessful()){

                    List<MovieResult> movies = response.body().getResults();
                    int id = 2;
                    for(MovieResult movieResult : movies){
                        String title = movieResult.getTitle();
                        String description = title + " " + context.getString(R.string.release_reminder_message);
                        showReleaseToday(context, title, description, id);
                        id++;
                    }

                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });



    }

    private void showReleaseToday(final Context context, String title, String desc, int id) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "Today release channel";

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_48dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications_black_48dp))
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(uriRingtone)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification notification = mBuilder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        if (mNotificationManager != null) {
            mNotificationManager.notify(id, notification);
        }
    }

    private void showDailyReminder(Context context) {
        int NOTIFICATION_ID = 1;
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "Daily Reminder channel";

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_48dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications_black_48dp))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.daily_reminder))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(uriRingtone)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = mBuilder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    private void cancelReminder(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY_REMINDER : ID_RELEASE_TODAY;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void cancelDailyReminder(Context context) {
        cancelReminder(context, TYPE_DAILY);
    }

    public void cancelReleaseToday(Context context) {
        cancelReminder(context, TYPE_RELEASE);
    }
}