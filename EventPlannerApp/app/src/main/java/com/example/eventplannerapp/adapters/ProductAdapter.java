package com.example.eventplannerapp.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.fragments.ProductFragment;

public class ProductAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private Product[] mProducts;

    public ProductAdapter(@NonNull FragmentManager fm, Context context, Product[] products) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        mProducts = products;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Return a new instance of ProductFragment for each product
        return ProductFragment.newInstance(mProducts[position]);
    }

    @Override
    public int getCount() {
        return mProducts.length;
    }
}
