package com.example.eventplannerapp.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.ActivityReportsBinding;
import com.example.eventplannerapp.databinding.ActivityVlasnikHomeBinding;
import com.example.eventplannerapp.receivers.SyncReceiver;
import com.example.eventplannerapp.services.SyncService;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

public class ReportsActivity extends AppCompatActivity {

    private static final String SYNC_CHANNEL_ID = "Sync channel";
    private ActivityReportsBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Set<Integer> topLevelDestinations = new HashSet<>();
    private SyncReceiver syncReceiver;
    public static String SYNC_DATA = "SYNC_DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityReportsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false); // Back button
            actionBar.setHomeAsUpIndicator(R.drawable.ic_hamburger);
            actionBar.setHomeButtonEnabled(true);
        }

        navController = Navigation.findNavController(this, R.id.fragment_nav_report_content);
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            Log.i("EventApp", "Destination Changed");
            int id = navDestination.getId();
            boolean isTopLevelDestination = topLevelDestinations.contains(id);
            if (!isTopLevelDestination) {
                if (id == R.id.nav_all_reports_page) {
                    Toast.makeText(ReportsActivity.this, "All reports", Toast.LENGTH_SHORT).show();
                }
                /*else if (id == R.id.nav_manage_products) {
                    Toast.makeText(ReportsActivity.this, "Manage products", Toast.LENGTH_SHORT).show();
                }*/
                // ... switch iz nekog razloga ne radi: `constant expression required`
                //drawer.closeDrawers();
            }
            else {
                // TODO: Sta ako jeste top level, mada ja nemam top level destinacija jos uvek tako da nista
            }
        });

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            setUpReceiver();
        }
        createNotificationChannel("Sync channel", "Channel for sync nottifications",
                SYNC_CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH);*/
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void setUpReceiver(){
        syncReceiver = new SyncReceiver();
        /*
         * Registrujemo nas BroadcastReceiver i dodajemo mu 'filter'.
         * Filter koristimo prilikom prispeca poruka. Jedan receiver
         * moze da reaguje na vise tipova poruka. One nam kazu
         * sta tacno treba da se desi kada poruka odredjenog tipa (filera)
         * stigne.
         * */
        IntentFilter filter = new IntentFilter();
        filter.addAction(SYNC_DATA);
        registerReceiver(syncReceiver, filter, RECEIVER_EXPORTED);
    }

    private void createNotificationChannel(CharSequence name, String description, String channel_id, int importance) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        NotificationChannel channel = new NotificationChannel(channel_id, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}