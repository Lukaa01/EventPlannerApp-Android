package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.EventListAdapter;
import com.example.eventplannerapp.activities.adapters.ProductListAdapter;
import com.example.eventplannerapp.databinding.FragmentEmployeeCalendarBinding;
import com.example.eventplannerapp.databinding.FragmentEmployeeEventListBinding;
import com.example.eventplannerapp.databinding.FragmentProductsPageBinding;
import com.example.eventplannerapp.model.Event;
import com.example.eventplannerapp.model.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeEventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeEventListFragment extends ListFragment {

    private EventListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<Event> mEvents;
    private FragmentEmployeeCalendarBinding binding;

    public EmployeeEventListFragment() {
        // Required empty public constructor
    }

    public static EmployeeEventListFragment newInstance(ArrayList<Event> events) {
        EmployeeEventListFragment fragment = new EmployeeEventListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, events);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEvents = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new EventListAdapter(getActivity(), mEvents);
            setListAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_event_list, container, false);
    }
}