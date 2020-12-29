package com.adivid.alarmmanagerexampletwo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    TimePicker timePicker;
    public static final String CHANNEL_ID = "exampleServiceChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = findViewById(R.id.timePicker);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                if (Build.VERSION.SDK_INT >= 23) {
                    calendar.set(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getHour(),
                            timePicker.getMinute(),
                            0
                    );
                } else {
                    calendar.set(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getCurrentHour(),
                            timePicker.getCurrentMinute(),
                            0
                    );
                }

                setAlarm(calendar.getTimeInMillis());
            }
        });

       /* findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent intent = new Intent(MainActivity.this, MyAlarmReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                        intent, 0);

                long timeAtButtonClick = System.currentTimeMillis();

                long tenSecsInMillis = 1000 * 10;

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeAtButtonClick, tenSecsInMillis, pendingIntent);

                Toast.makeText(MainActivity.this, "Alarm Set", Toast.LENGTH_SHORT).show();

            }
        });
*/

       findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, TransitionActivity.class));

           }
       });

       findViewById(R.id.buttonService).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Calendar calendar = Calendar.getInstance();
               if (Build.VERSION.SDK_INT >= 23) {
                   calendar.set(
                           calendar.get(Calendar.YEAR),
                           calendar.get(Calendar.MONTH),
                           calendar.get(Calendar.DAY_OF_MONTH),
                           timePicker.getHour(),
                           timePicker.getMinute(),
                           0
                   );
               } else {
                   calendar.set(
                           calendar.get(Calendar.YEAR),
                           calendar.get(Calendar.MONTH),
                           calendar.get(Calendar.DAY_OF_MONTH),
                           timePicker.getCurrentHour(),
                           timePicker.getCurrentMinute(),
                           0
                   );
               }

               startCustomService(calendar.getTimeInMillis());
           }
       });

    }

    private void startCustomService(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, ExampleService.class);

        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            pendingIntent = PendingIntent.getForegroundService(this, 0,
                    intent, 0);
        }else{
            pendingIntent = PendingIntent.getService(this, 0,
                    intent, 0);
        }
/*
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis,
                AlarmManager.INTERVAL_DAY , pendingIntent);*/

        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);


        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();

    }

    private void setAlarm(long timeInMillis) {
/*        ComponentName receiver = new ComponentName(getApplicationContext(), MyAlarmReceiverTwo.class);
        PackageManager pm = getApplicationContext().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);*/

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, MyAlarmReceiverTwo.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, 0);

        if(Build.VERSION.SDK_INT >=23){
            Log.d(TAG, "setExactAndAllowWhileIdle: ");
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);


        }else{
            if (Build.VERSION.SDK_INT >= 19) {
                Log.d(TAG, "setExact");
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
            } else {
                Log.d(TAG, "set");
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
            }
        }

        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();

    }
}