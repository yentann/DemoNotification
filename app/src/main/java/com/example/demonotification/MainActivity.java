package com.example.demonotification;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.app.Notification.*;

public class MainActivity extends AppCompatActivity {

    int requestCode = 123;
    int notificationID = 888;
    Button btnNotify1, btnNotify2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNotify1 = findViewById(R.id.btnNoti1);

        btnNotify2 = findViewById(R.id.btnNoti2);

        btnNotify1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationManager notificationManager = (NotificationManager)
                        getSystemService(NOTIFICATION_SERVICE);

                //Channels only exist in Orea
                // This is a channel wide setting. Create another channel if you need different importance level IMPORTANCE_DEFAULT
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);

                    channel.setDescription("This is for default notification");
                    notificationManager.createNotificationChannel(channel);
                }

                //The Activity to be launched, if the notification is clicked on
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity( MainActivity.this, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                // Build notification (default Channel is now a compulsory argument in the builder) //set title and text when notification appear it will appear
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "default");
                builder.setContentTitle("Amazing Offer!");
                builder.setContentText("Subject");
                builder.setSmallIcon(android.R.drawable.btn_star_big_off);
                builder.setContentIntent(pIntent);
                builder.setAutoCancel(true);


                //The sound can be set without high priority, if the intention is to set sound, not Heads-up. Line 57 to 61
                Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(uri);

                builder.setPriority(PRIORITY_HIGH);


                Notification n = builder.build();

                // An integer good to have, for you to programmatically cancel it
                notificationManager.notify(notificationID, n);
                finish();
            }
        });



        btnNotify2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription("This is for default notification");
                    notificationManager.createNotificationChannel(channel);
                }

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                bigText.setBigContentTitle("Big Text - Long Content");
                bigText.bigText("This is one big text" + "- A quick brown fox jumps over a lazy brown dog" + "\nLorem ipsum dolor sit amet, sea eu quod des");
                bigText.setSummaryText("Reflection Journal?");

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "default");
                builder.setContentTitle("Amazing Offer!");
                builder.setContentText("Subject");
                builder.setSmallIcon(android.R.drawable.btn_star_big_off);
                builder.setContentIntent(pendingIntent);
                builder.setStyle(bigText);
                builder.setAutoCancel(true);

                Notification n = builder.build();

                notificationManager.notify(notificationID, n);
                finish();
            }

        });

    }
}

