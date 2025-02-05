package com.example.eventplannerapp.fragments.vlasnik;

import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.FragmentPriceListBinding;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Service;

import java.util.ArrayList;

public class ServicePricelistFragment extends ListFragment {
    private static final String ARG_PARAM = "param";
    private ArrayList<Service> mServices;
    private ServicePricelistAdapter priceListAdapter;

    public ServicePricelistFragment() {
    }

    public static ServicePricelistFragment newInstance(ArrayList<Service> services) {
        ServicePricelistFragment fragment = new ServicePricelistFragment();
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
            priceListAdapter = new ServicePricelistAdapter(getActivity(), mServices);
            setListAdapter(priceListAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service_pricelist, container, false);
    }
}