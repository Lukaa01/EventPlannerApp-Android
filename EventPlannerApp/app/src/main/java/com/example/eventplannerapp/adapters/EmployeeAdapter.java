package com.example.eventplannerapp.adapters;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.eventplannerapp.model.Employee;

import org.jetbrains.annotations.NotNull;

public class EmployeeAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private Employee[] mEmployees;

    public EmployeeAdapter(@NotNull FragmentManager fm, Context context, Employee[] employees) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        mEmployees = employees;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null; // TODO
    }

    @Override
    public int getCount() {
        return mEmployees.length;
    }
}
