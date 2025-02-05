package com.example.eventplannerapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.FragmentItemListBinding;
import com.example.eventplannerapp.databinding.FragmentPriceListBinding;
import com.example.eventplannerapp.fragments.placeholder.PlaceholderContent;
import com.example.eventplannerapp.fragments.vlasnik.PriceListAdapter;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Report;

import java.util.ArrayList;

public class ReportFragment extends ListFragment {
    private static final String ARG_PARAM = "param";
    private ArrayList<Report> mReports;
    private ReportAdapter reportAdapter;
    private NavController navController;
    private FragmentItemListBinding binding;

    public ReportFragment() {
    }

    public static ReportFragment newInstance(ArrayList<Report> reports) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, reports);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mReports = getArguments().getParcelableArrayList(ARG_PARAM);
            reportAdapter = new ReportAdapter(getActivity(), mReports);
            setListAdapter(reportAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }
}