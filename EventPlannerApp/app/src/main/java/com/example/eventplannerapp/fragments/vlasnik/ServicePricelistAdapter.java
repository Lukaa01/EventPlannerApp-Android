package com.example.eventplannerapp.fragments.vlasnik;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.FragmentActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.VlasnikHomeActivity;
import com.example.eventplannerapp.fragments.vlasnik.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.eventplannerapp.databinding.FragmentPriceListBinding;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ServicePricelistAdapter extends ArrayAdapter<Service> {

    private final ArrayList<Service> aServices;
    public ServicePricelistAdapter(Context c, ArrayList<Service> services) {
        super(c, R.layout.fragment_service_pricelist, services);
        aServices = services;
    }
    @Override
    public int getCount() {
        return aServices.size();
    }

    @Nullable
    @Override
    public Service getItem(int position) {
        return aServices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Service service = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_price_list,
                    parent, false);
        }

        TextView serviceTitle = convertView.findViewById(R.id.pricelist_name);
        TextView servicePrice = convertView.findViewById(R.id.pricelist_price);
        TextView discount = convertView.findViewById(R.id.pricelist_discount);
        TextView discountedPrice = convertView.findViewById(R.id.pricelist_price_with_discount);
        Button btn = convertView.findViewById(R.id.change_price_button);

        if(service != null){
            serviceTitle.setText(service.getTitle());
            String priceText = String.valueOf(service.getPrice());
            priceText = priceText + " din";
            String discountText = String.valueOf(service.getDiscount());
            discountText = discountText + " %";
            String discountedPriceText = String.valueOf(service.getDiscountedPrice());
            discountedPriceText = discountedPriceText + " din";
            servicePrice.setText(priceText);
            discount.setText(discountText);
            discountedPrice.setText(discountedPriceText);
            View finalConvertView = convertView;
            btn.setOnClickListener(c -> {
                NavController navController = Navigation.findNavController(finalConvertView);
                Bundle bundle = new Bundle();
                bundle.putParcelable("service", service);
                navController.navigate(R.id.nav_update_service_pricelist, bundle);
            });
        }

        return convertView;
    }

}