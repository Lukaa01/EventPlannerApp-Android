package com.example.eventplannerapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.adapters.CategoryAdapter;
import com.example.eventplannerapp.fragments.CategoryFragment;

import java.util.ArrayList;
import java.util.List;

public class HandleCategoriesActivity extends AppCompatActivity {
    private CategoryAdapter adapter;
    private List<String> categoryNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_categories);
        Button addCategoryButton = findViewById(R.id.addCategoryButton);
        Button addSubcategoryButton = findViewById(R.id.addSubcategoryButton);

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HandleCategoriesActivity.this, AddCategoryActivity.class);
                startActivity(intent);
            }
        });

        addSubcategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HandleCategoriesActivity.this, AddSubcategoryActivity.class);
                startActivity(intent);
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        String[] categoryNames = {
                "Catering, Food, and Cakes",
                "Accommodation",
                "Music and Entertainment",
                "Photo and Video",
                "Decoration and Lighting",
                "Clothing and Styling",
                "Beauty and Care",
                "Planning and Coordination",
                "Invitations and Stationery",
                "Logistics and Security"
        };

        String[] subcategoryNames = {
                "Catering and Food Preparation",
                "Menu Planning",
                "Rental of Catering Venues for Events",
                "Event Catering",
                "Beverages",
                "Cakes, Sweets, and Fruit",
                "Specialty Products",
                "Photography",
                "Videography",
                "Drone Footage",
                "Post-production / Editing",
                "Photos and Albums",
                "Video Materials",
                "Digital Products",
                "Additional Products"
        };



        for (int i = 0; i < 10; i++) {
            CategoryFragment fragment = new CategoryFragment();
            fragment.setCategoryName(categoryNames[i]);
            fragmentTransaction.add(R.id.fragment_container, fragment, "Fragment_" + i);
        }
        for (int i = 0; i < subcategoryNames.length; i++) {
            CategoryFragment fragment = new CategoryFragment();
            fragment.setCategoryName(subcategoryNames[i]);
            fragmentTransaction.add(R.id.sub_fragment_container, fragment, "SubFragment_" + i);
        }

            fragmentTransaction.commit();


        //adapter = new CategoryAdapter(categoryNames);
    }
}