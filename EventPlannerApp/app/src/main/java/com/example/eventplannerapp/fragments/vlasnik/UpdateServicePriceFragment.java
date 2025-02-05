package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Service;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateServicePriceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateServicePriceFragment extends Fragment {
    private static final String ARG_PARAM1 = "service";
    private Service service;

    public UpdateServicePriceFragment() {
        // Required empty public constructor
    }

    public static UpdatePriceFragment newInstance(Service service) {
        UpdatePriceFragment fragment = new UpdatePriceFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, service);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            service = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_service_price, container, false);
        TextView price = view.findViewById(R.id.pricelistServicePriceEditText);
        price.setText(String.valueOf(service.getPrice()));
        TextView discount = view.findViewById(R.id.pricelistServiceDiscountEditText);
        discount.setText(String.valueOf(service.getDiscount()));
        Button btnUpdate = view.findViewById(R.id.update_service_price_button);
        btnUpdate.setOnClickListener(l -> {
            service.setPrice(Integer.parseInt(price.getText().toString()));
            service.setDiscount(Integer.parseInt(discount.getText().toString()));
            service.setDiscountedPrice(Integer.parseInt(price.getText().toString()) * (100 - Integer.parseInt(discount.getText().toString())) / 100);
            CloudStoreUtil.updateService(service);
            Toast.makeText(getContext(), "Service successfully updated!", Toast.LENGTH_SHORT).show();
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.nav_service_pricelist);
        });
        return view;
    }
}