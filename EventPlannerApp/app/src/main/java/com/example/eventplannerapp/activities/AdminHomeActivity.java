package com.example.eventplannerapp.activities;

import android.os.Bundle;
import android.widget.Toast;

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

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.ActivityAdminHomeBinding;
import com.example.eventplannerapp.fragments.admin.RejectionReasonDialogFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

public class AdminHomeActivity extends AppCompatActivity implements RejectionReasonDialogFragment.RejectionReasonDialogListener{
    private ActivityAdminHomeBinding binding;
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

        binding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        drawer = binding.adminDrawer;
        navigationView = binding.navViewOrg;
        toolbar = binding.activityAdminHomeBase.toolbarOrg;

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_hamburger);
            actionBar.setHomeButtonEnabled(true);
        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navController = Navigation.findNavController(this, R.id.fragment_nav_admin);
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            int id = navDestination.getId();
            boolean isTopLevelDestination = topLevelDestinations.contains(id);
            if (!isTopLevelDestination) {
                if (id == R.id.nav_categories_view) {
                    Toast.makeText(AdminHomeActivity.this, "Manage employees", Toast.LENGTH_SHORT).show();
                }
                else if (id == R.id.nav_events_view) {
                    Toast.makeText(AdminHomeActivity.this, "Manage products", Toast.LENGTH_SHORT).show();
                }
                drawer.closeDrawers();
            }
        });
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_categories_view, R.id.nav_events_view)
                .setOpenableLayout(drawer)
                .build();

        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    }

    @Override
    public void onDialogPositiveClick(String rejectionReason) {

    }
}
