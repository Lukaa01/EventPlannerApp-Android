package com.example.eventplannerapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.example.eventplannerapp.R; // Replace with your actual package name
import com.google.android.material.textview.MaterialTextView;

import com.example.eventplannerapp.model.Product;

public class ProductFragment extends Fragment {
    private MaterialTextView categoryTextView;
    private MaterialTextView subcategoryTextView;

    // Method to populate views with data
    private void populateViews(Product product) {
        // Set text for each TextView
        categoryTextView.setText(product.getCategory());
        subcategoryTextView.setText(product.getSubcategory());
        // Set text for other TextViews with corresponding data from the product object
    }

    // newInstance method to create instances of ProductFragment with different data
    public static ProductFragment newInstance(Product product) {
        return new ProductFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        // Initialize TextViews and other views
        categoryTextView = view.findViewById(R.id.category);
        subcategoryTextView = view.findViewById(R.id.subcategory);
        // Initialize other TextViews and views for other data

        // Get the Product object from arguments
        if (getArguments() != null) {
            Product product = getArguments().getParcelable("product");
            // Populate views with data
            populateViews(product);
        }

        return view;
    }
}

