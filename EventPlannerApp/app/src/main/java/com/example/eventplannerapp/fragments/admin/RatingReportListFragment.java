package com.example.eventplannerapp.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.RatingListAdapter;
import com.example.eventplannerapp.activities.adapters.RatingReportListAdapter;
import com.example.eventplannerapp.databinding.FragmentManageRatingReportsBinding;
import com.example.eventplannerapp.fragments.vlasnik.OnRatingReportSelectedListener;
import com.example.eventplannerapp.fragments.vlasnik.OnRatingSelectedListener;
import com.example.eventplannerapp.model.RatingReport;

import java.util.ArrayList;

public class RatingReportListFragment extends ListFragment {

    private RatingReportListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<RatingReport> mReports;
    private FragmentManageRatingReportsBinding binding;
    private OnRatingReportSelectedListener mListener;


    public RatingReportListFragment() {
    }

    public void setOnReportSelectedListener(OnRatingReportSelectedListener listener) {
        mListener = listener;
    }

    public static RatingReportListFragment newInstance(ArrayList<RatingReport> reports, OnRatingReportSelectedListener listener) {
        RatingReportListFragment fragment = new RatingReportListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, reports);
        fragment.setArguments(args);
        fragment.setOnReportSelectedListener(listener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mReports = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new RatingReportListAdapter(getActivity(), mReports);
            adapter.setOnRatingSelectedListener(rating -> {
                if (mListener != null) {
                    mListener.onRatingReportSelected(rating);
                }
            });
            setListAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rating_list, container, false);
    }
}