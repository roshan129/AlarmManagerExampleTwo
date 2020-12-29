package com.adivid.alarmmanagerexampletwo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;




public class ExampleService extends Service {

    private static final String TAG = "ExampleService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: service cretaed");
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*String string = intent.getStringExtra("inputExtra");*/

       /* Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("This is a Title")
                .setContentText("description")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();*/


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationManager manager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("alarmmanagerexampletwo", "alarmmanagerexampletwo",
                    NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);

        }

        Notification builder = new NotificationCompat.Builder(getApplicationContext(), "alarmmanagerexampletwo")
                .setContentTitle("title service")
                .setContentText("description service")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        /*manager.notify(1, builder.build());*/

        startForeground(1, builder);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                stopSelf();
            }
        },  60 * 1000);


        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: in bind");
        return null;
    }
}
