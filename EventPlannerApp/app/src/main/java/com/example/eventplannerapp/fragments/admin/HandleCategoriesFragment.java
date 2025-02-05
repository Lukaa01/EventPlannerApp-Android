package com.example.eventplannerapp.fragments.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.AddCategoryActivity;
import com.example.eventplannerapp.activities.AddSubcategoryActivity;
import com.example.eventplannerapp.fragments.CategoryFragment;

public class HandleCategoriesFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_handle_categories, container, false);

        Button addCategoryButton = view.findViewById(R.id.addCategoryButton);
        Button addSubcategoryButton = view.findViewById(R.id.addSubcategoryButton);

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
                startActivity(intent);
            }
        });

        addSubcategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddSubcategoryActivity.class);
                startActivity(intent);
            }
        });

        FragmentManager fragmentManager = getChildFragmentManager();
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

        for (int i = 0; i < categoryNames.length; i++) {
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

        return view;
    }
}
