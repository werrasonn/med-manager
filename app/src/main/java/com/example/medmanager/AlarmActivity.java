package com.example.medmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alarm);

    TextView userName, medName, medTime, medQty;
    Button tookMed, snooze;

    userName = findViewById(R.id.alarm_user_name);
    medName = findViewById(R.id.alarm_med_name);
    medTime = findViewById(R.id.alarm_med_time);
    medQty = findViewById(R.id.alarm_med_quantity);

    tookMed = findViewById(R.id.alarm_took);
   // snooze = findViewById(R.id.alarm_snooze);

    Intent intent = getIntent();
    medName.setText(intent.getStringExtra("medName"));
    medTime.setText("Time: "+intent.getStringExtra("medTime"));
    medQty.setText("Qty: "+intent.getStringExtra("medQty"));
    userName.setText(intent.getStringExtra("userName"));

    tookMed.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(AlarmActivity.this, AlarmBroadcastReceiver .class); // Replace with your AlarmReceiver class
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);

        // Cancel the notification using NotificationManager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(AlarmActivity.this);
        notificationManager.cancel(1); // Replace NOTIFICATION_ID with your notification ID

        finish();
      }
    });

//    snooze.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Toast.makeText(getApplicationContext(),"Reminder set after 10 minutes.",Toast.LENGTH_LONG).show();
//        finish();
//      }
//    });


  }
}