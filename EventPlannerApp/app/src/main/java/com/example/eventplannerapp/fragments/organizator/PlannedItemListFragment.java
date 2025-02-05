package com.example.eventplannerapp.fragments.organizator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.EventListAdapter1;
import com.example.eventplannerapp.activities.adapters.PlannedItemsListAdapter;
import com.example.eventplannerapp.model.BudgetItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlannedItemListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlannedItemListFragment extends ListFragment {
    private PlannedItemsListAdapter adapter;
    private ArrayList<BudgetItem> mItems;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlannedItemListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlannedItemList.
     */
    // TODO: Rename and change types and number of parameters
    public static PlannedItemListFragment newInstance(ArrayList<BudgetItem> items) {
        PlannedItemListFragment fragment = new PlannedItemListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, items);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItems = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new PlannedItemsListAdapter(getActivity(), mItems);
            setListAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planned_item_list, container, false);
    }
}