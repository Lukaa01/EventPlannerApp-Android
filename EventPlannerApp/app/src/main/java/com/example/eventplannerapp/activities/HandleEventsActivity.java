package com.example.eventplannerapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.fragments.EventCategoryFragment;
import com.example.eventplannerapp.fragments.EventFragment;

public class HandleEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_events);

        Button addEventButton = findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HandleEventsActivity.this, AddEventTypeActivity.class);
                startActivity(intent);
            }
        });

        EventCategoryFragment privateEventsFragment = new EventCategoryFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, privateEventsFragment).commit();

        EventCategoryFragment corporateEventsFragment = new EventCategoryFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, corporateEventsFragment).commit();

        EventCategoryFragment educationEventsFragment = new EventCategoryFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, educationEventsFragment).commit();

        EventCategoryFragment culturalEventsFragment = new EventCategoryFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, culturalEventsFragment).commit();

        EventCategoryFragment sportsEventsFragment = new EventCategoryFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, sportsEventsFragment).commit();

        EventCategoryFragment humanitarianEventsFragment = new EventCategoryFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, humanitarianEventsFragment).commit();

        EventCategoryFragment governmentEventsFragment = new EventCategoryFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, governmentEventsFragment).commit();

    }
}