package com.avagar.sporty.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.avagar.sporty.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class FirebaseService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel notificationChannel = new NotificationChannel("default", "Sporty Push Notifications", NotificationManager.IMPORTANCE_HIGH);

                notificationChannel.setDescription("Sporty");
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Notification.DEFAULT_LIGHTS);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{0, 100, 100, 100, 100, 100});
                notificationManager.createNotificationChannel(notificationChannel);
            }

            NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "default")
                    .setContentTitle("Ayo morty exeis eidopoihsh ")
                    .setContentText("eisai mortys")
                    .setSmallIcon(R.drawable.ic_baseline_delete_24)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

            int notificationId = new Random().nextInt();

            PendingIntent pendingIntent = new NavDeepLinkBuilder(getApplicationContext())
                    .setGraph(R.navigation.navigation_graph)
                    .setDestination(R.id.athletesFragment)
                    .createPendingIntent();

            notification.setContentIntent(pendingIntent);

            notificationManager.notify(notificationId, notification.build());
        }
    }
}
