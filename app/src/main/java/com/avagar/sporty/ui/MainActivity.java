package com.avagar.sporty.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.avagar.sporty.R;
import com.avagar.sporty.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.athletesFragment, R.id.clubsFragment, R.id.matchesFragment, R.id.sportsFragment)
                        .setOpenableLayout(binding.mainDrawer)
                        .build();

        binding.mainToolbar.setTitle("Sporty");
        setSupportActionBar(binding.mainToolbar);

        navController = Navigation.findNavController(this, R.id.main_host);

        NavigationUI.setupWithNavController(binding.mainToolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.mainNavigation, navController);
        createNotificationChannel();
    }

    @Override
    public void onBackPressed() {
        if (binding.mainDrawer.isDrawerOpen(GravityCompat.START)) {
            binding.mainDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void createNotificationChannel() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
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
        }
    }

    public void sendPush(String title, String description) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "default")
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_baseline_delete_24)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        int notificationId = new Random().nextInt();

        PendingIntent pendingIntent = new NavDeepLinkBuilder(this.getApplicationContext())
                .setGraph(R.navigation.navigation_graph)
                .setDestination(R.id.athletesFragment)
                .createPendingIntent();

        notification.setContentIntent(pendingIntent);

        notificationManager.notify(notificationId, notification.build());
    }
}