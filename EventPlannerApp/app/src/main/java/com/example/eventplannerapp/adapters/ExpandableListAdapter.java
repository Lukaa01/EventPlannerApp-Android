package com.example.eventplannerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.eventplannerapp.R;

import java.util.List;

public class ExpandableListAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> groupTitles;

    public ExpandableListAdapter(Context context, List<String> groupTitles) {
        super(context, R.layout.type_layout, groupTitles);
        this.groupTitles = groupTitles;
    }
}