package com.example.eventplannerapp.fragments.organizator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.EventListAdapter1;
import com.example.eventplannerapp.activities.adapters.GuestListAdapter;
import com.example.eventplannerapp.model.Event1;
import com.example.eventplannerapp.model.Guest;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestListFragment extends ListFragment {

    private GuestListAdapter adapter;

    private ArrayList<Guest> mGuests;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private static final String ARG_PARAM = "param";


    public GuestListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuestListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuestListFragment newInstance(ArrayList<Guest> guests) {
        GuestListFragment fragment = new GuestListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, guests);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGuests = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new GuestListAdapter(getActivity(), mGuests);
            setListAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest_list, container, false);
    }
}