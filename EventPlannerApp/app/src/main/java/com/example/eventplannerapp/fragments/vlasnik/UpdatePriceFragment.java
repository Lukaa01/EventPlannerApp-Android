package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.Product;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdatePriceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePriceFragment extends Fragment {
    private static final String ARG_PARAM1 = "product";
    private Product product;

    public UpdatePriceFragment() {
        // Required empty public constructor
    }

    public static UpdatePriceFragment newInstance(Product product) {
        UpdatePriceFragment fragment = new UpdatePriceFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_price, container, false);
        TextView price = view.findViewById(R.id.pricelistPriceEditText);
        price.setText(String.valueOf(product.getPrice()));
        TextView discount = view.findViewById(R.id.pricelistDiscountEditText);
        discount.setText(String.valueOf(product.getDiscount()));
        Button btnUpdate = view.findViewById(R.id.update_price_button);
        btnUpdate.setOnClickListener(l -> {
            product.setPrice(Integer.parseInt(price.getText().toString()));
            product.setDiscount(Integer.parseInt(discount.getText().toString()));
            product.setDiscountedPrice(Integer.parseInt(price.getText().toString()) * (100 - Integer.parseInt(discount.getText().toString())) / 100);
            CloudStoreUtil.updateProduct(product);
            Toast.makeText(getContext(), "Product successfully updated!", Toast.LENGTH_SHORT).show();
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.nav_pricelist);
        });
        return view;
    }
}