package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.ProductListAdapter;
import com.example.eventplannerapp.activities.adapters.ServiceListAdapter;
import com.example.eventplannerapp.databinding.FragmentProductsPageBinding;
import com.example.eventplannerapp.databinding.FragmentServicePageBinding;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Service;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceListFragment extends ListFragment {

    private ServiceListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<Service> mServices;
    private FragmentServicePageBinding binding;

    public ServiceListFragment() {
        // Required empty public constructor
    }

    public static ServiceListFragment newInstance(ArrayList<Service> services) {
        ServiceListFragment fragment = new ServiceListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, services);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mServices = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new ServiceListAdapter(getActivity(), mServices);
            setListAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service_list, container, false);
    }
}