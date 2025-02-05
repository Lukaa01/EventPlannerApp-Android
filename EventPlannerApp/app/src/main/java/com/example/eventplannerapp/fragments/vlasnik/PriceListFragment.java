package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.FragmentPriceListBinding;
import com.example.eventplannerapp.databinding.FragmentPriceListListBinding;
import com.example.eventplannerapp.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class PriceListFragment extends ListFragment {
    private static final String ARG_PARAM = "param";
    private ArrayList<Product> mProducts;
    private PriceListAdapter priceListAdapter;
    private NavController navController;
    private FragmentPriceListBinding binding;

    public PriceListFragment() {
    }

    public static PriceListFragment newInstance(ArrayList<Product> products) {
        PriceListFragment fragment = new PriceListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, products);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mProducts = getArguments().getParcelableArrayList(ARG_PARAM);
            priceListAdapter = new PriceListAdapter(getActivity(), mProducts);
            setListAdapter(priceListAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_price_list_list, container, false);
    }
}