package com.example.eventplannerapp.activities;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.ActivityEmployeeHomeBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

public class EmployeeHomeActivity extends AppCompatActivity {
    private ActivityEmployeeHomeBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Set<Integer> topLevelDestinations = new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEmployeeHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        drawer = binding.drawerLayoutEmployee;
        navigationView = binding.navViewEmployee;
        toolbar = binding.activityEmployeeHomeBase.toolbarEmployee;

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false); // Back button
            actionBar.setHomeAsUpIndicator(R.drawable.ic_hamburger);
            actionBar.setHomeButtonEnabled(true);
        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main_employee);
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            Log.i("EventApp", "Destination Changed");
            int id = navDestination.getId();
            boolean isTopLevelDestination = topLevelDestinations.contains(id);
            if (!isTopLevelDestination) {
                if (id == R.id.nav_events) {
                    Toast.makeText(EmployeeHomeActivity.this, "Events", Toast.LENGTH_SHORT).show();

                }
                // ... switch iz nekog razloga ne radi: `constant expression required`
                drawer.closeDrawers();
            }
            else {
                // TODO: Sta ako jeste top level, mada ja nemam top level destinacija jos uvek tako da nista
            }
        });
        mAppBarConfiguration = new AppBarConfiguration
                .Builder(R.id.nav_events_view, R.id.nav_createEvent,R.id.nav_manage_products, R.id.nav_manage_services, R.id.nav_manage_packages)
                .setOpenableLayout(drawer)
                .build();
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    }
}