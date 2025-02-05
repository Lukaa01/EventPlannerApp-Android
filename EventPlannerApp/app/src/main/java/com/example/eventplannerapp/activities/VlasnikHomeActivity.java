package com.example.eventplannerapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import com.example.eventplannerapp.databinding.ActivityVlasnikHomeBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

public class VlasnikHomeActivity extends AppCompatActivity {

    private ActivityVlasnikHomeBinding binding;
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
//        setContentView(R.layout.activity_vlasnik_home);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        binding = ActivityVlasnikHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ovde mozes namestiti floatingActionButton: binding.activityVlasnikHomeBase... onclick...

        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        toolbar = binding.activityVlasnikHomeBase.toolbar;

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

        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            Log.i("EventApp", "Destination Changed");
            int id = navDestination.getId();
            boolean isTopLevelDestination = topLevelDestinations.contains(id);
            if (!isTopLevelDestination) {
                if (id == R.id.nav_vlasnik_manage_employees) {
                    //Toast.makeText(VlasnikHomeActivity.this, "Manage employees", Toast.LENGTH_SHORT).show();
                }
                else if (id == R.id.nav_manage_products) {
                    //Toast.makeText(VlasnikHomeActivity.this, "Manage products", Toast.LENGTH_SHORT).show();
                }
                else if (id == R.id.nav_manage_services) {
                    //Toast.makeText(VlasnikHomeActivity.this, "Manage services", Toast.LENGTH_SHORT).show();
                }
                else if (id == R.id.nav_manage_packages) {
                    //Toast.makeText(VlasnikHomeActivity.this, "Manage packages", Toast.LENGTH_SHORT).show();
                }
                else if (id == R.id.nav_pricelist) {
                    Toast.makeText(VlasnikHomeActivity.this, "Pricelist", Toast.LENGTH_SHORT).show();
                }
                // ... switch iz nekog razloga ne radi: `constant expression required`
                drawer.closeDrawers();
            }
            else {
                // TODO: Sta ako jeste top level, mada ja nemam top level destinacija jos uvek tako da nista
            }
        });

        mAppBarConfiguration = new AppBarConfiguration
                .Builder(R.id.nav_vlasnik_manage_employees, R.id.nav_manage_products, R.id.nav_manage_services, R.id.nav_manage_packages)
                .setOpenableLayout(drawer)
                .build();
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);








    }

    // Ovo napravi onaj meni sa tri tacke koji radi isto sto i burgir meni.. nepotrebno je za sad
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // menu.clear();
//        // koristimo ako je nasa arhitekrura takva da imamo jednu aktivnost
//        // i vise fragmentaa gde svaki od njih ima svoj menu unutar toolbar-a
//
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.nav_menu, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
//        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
//    }
//    @Override
//    public boolean onSupportNavigateUp() {
//        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
//    }
}