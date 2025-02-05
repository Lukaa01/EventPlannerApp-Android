package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.ListFragment;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.RatingListAdapter;
import com.example.eventplannerapp.databinding.FragmentManageRatingsBinding;
import com.example.eventplannerapp.model.Rating;

import java.util.ArrayList;
import java.util.List;

public class RatingListFragment extends ListFragment {

    private RatingListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<Rating> mRatings;
    private FragmentManageRatingsBinding binding;
    private OnRatingSelectedListener mListener;



    public void setOnRatingSelectedListener(OnRatingSelectedListener listener) {
        mListener = listener;
    }

    public RatingListFragment() {
    }

    public static RatingListFragment newInstance(ArrayList<Rating> ratings, OnRatingSelectedListener listener) {
        RatingListFragment fragment = new RatingListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, ratings);
        fragment.setArguments(args);
        fragment.setOnRatingSelectedListener(listener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRatings = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new RatingListAdapter(getActivity(), mRatings);
            adapter.setOnRatingSelectedListener(rating -> {
                if (mListener != null) {
                    mListener.onRatingSelected(rating);
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