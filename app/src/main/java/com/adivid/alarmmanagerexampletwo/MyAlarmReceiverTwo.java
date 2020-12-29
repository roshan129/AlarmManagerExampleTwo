package com.adivid.alarmmanagerexampletwo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class MyAlarmReceiverTwo extends BroadcastReceiver {

    private static final String TAG = "MyAlarm";
    int t = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("alarmmanagerexampletwo", "alarmmanagerexampletwo",
                    NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "alarmmanagerexampletwo")
                .setContentTitle("Title second")
                .setContentText("description second")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher);

        manager.notify(1, builder.build());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                t++;
                Log.d(TAG, "run: " + t);
                if(t<15) {
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);

/*        Intent serviceIntent = new Intent(context, ExampleService.class);
        ContextCompat.startForegroundService(context, serviceIntent);*/


    }
}
