package com.example.medmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;


import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;
import static com.example.medmanager.App.CHANNEL_ID;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    private NotificationManagerCompat notificationManager;
    public static int NOTIFICATION_NUMBER = 1;

    Context context;


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String medName = intent.getStringExtra("medName");
        String medQty = intent.getStringExtra("medQty");
        String userName = intent.getStringExtra("userName");

        MediaPlayer player = MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI);
        player.start();


        notificationManager = NotificationManagerCompat.from(context);
        sendOnChannel(context, userName + ", please take " + medQty + " dose of " + medName + ".", intent);

    }

    public void sendOnChannel(Context context, String message, Intent intent) {
        Intent resultIntent = new Intent(context, AlarmActivity.class);
        resultIntent.putExtra("medName", intent.getStringExtra("medName"));
        resultIntent.putExtra("medTime", intent.getStringExtra("medTime"));
        resultIntent.putExtra("medQty", intent.getStringExtra("medQty"));
        resultIntent.putExtra("userName", intent.getStringExtra("userName"));
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 1, resultIntent,  PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_add_alarm_24)
                .setContentTitle("Medicine Reminder")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .build();
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_NUMBER++, notification);
        }
    }
