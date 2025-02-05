package com.example.eventplannerapp.fragments.organizator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.EventListAdapter1;
import com.example.eventplannerapp.model.Event1;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFragment extends ListFragment {

    private EventListAdapter1 adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<Event1> mEvents;

    public EventListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventListFragment newInstance(ArrayList<Event1> events) {
        EventListFragment fragment = new EventListFragment();
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
            adapter = new EventListAdapter1(getActivity(), mEvents);
            setListAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }
}