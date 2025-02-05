package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.PackageListAdapter;
import com.example.eventplannerapp.databinding.FragmentPackageListBinding;
import com.example.eventplannerapp.model.Package;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PackageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PackageListFragment extends ListFragment {

    private PackageListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<Package> mPackages;
    private FragmentPackageListBinding binding;

    public PackageListFragment() {
        // Required empty public constructor
    }

    public static PackageListFragment newInstance(ArrayList<Package> packages) {
        PackageListFragment fragment = new PackageListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, packages);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPackages = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new PackageListAdapter(getActivity(), mPackages);
            setListAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_package_list, container, false);
    }
}