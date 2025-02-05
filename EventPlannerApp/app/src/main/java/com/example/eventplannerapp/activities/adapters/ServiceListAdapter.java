package com.example.eventplannerapp.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Service;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ServiceListAdapter extends ArrayAdapter<Service> {
    private ArrayList<Service> aServices;
    private NavController navController;
    private Consumer<Service> mListener;



    public ServiceListAdapter(Context context, ArrayList<Service> services){
        super(context, R.layout.service_card, services);
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

        if(service.isAvailable()) {
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.service_card,
                        parent, false);
            }
            LinearLayout productCard = convertView.findViewById(R.id.service_card_item);
            TextView productTitle = convertView.findViewById(R.id.product_name);
            TextView productDescription = convertView.findViewById(R.id.product_description);
            TextView productPrice = convertView.findViewById(R.id.product_price);
            TextView serviceLocation = convertView.findViewById(R.id.service_location);

            if(service != null){
                productTitle.setText(service.getTitle());
                productDescription.setText(service.getDescription());
                String priceText = String.valueOf(service.getPrice());
                priceText = priceText + " din";
                productPrice.setText(priceText);
                serviceLocation.setText(service.getLocation());
                productCard.setOnClickListener(v -> {
                    // Handle click on the item at 'position'
                    Log.i("ShopApp", "Clicked: " + service.getTitle() + ", id: " +
                            service.getId().toString());
                    Toast.makeText(getContext(), "Clicked: " + service.getTitle()  +
                            ", id: " + service.getId().toString(), Toast.LENGTH_SHORT).show();


                Context context = getContext();
                int navControllerId;

                if (context instanceof Activity) {
                    // Ako je trenutni kontekst aktivnost, možete dobiti referencu na tu aktivnost
                    Activity activity = (Activity) context;

                    // Onda možete dobiti informacije o trenutnoj aktivnosti
                    String activityName = activity.getClass().getSimpleName();

                    // Sada možete koristiti ime aktivnosti za logiku koja vam je potrebna
                    if (activityName.equals("VlasnikHomeActivity")) {
                        navController = Navigation.findNavController((Activity) getContext(), R.id.fragment_nav_content_main);
                    }
                    else {
                        navController = Navigation.findNavController((Activity) getContext(), R.id.fragment_nav_content_main_org);

                    }
                }

                Bundle bundle = new Bundle();
                bundle.putParcelable("selectedService", service);
                navController.navigate(R.id.nav_service_detail, bundle);
            });
        }

            return convertView;
        }
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.service_unavailable_card,
                    parent, false);
        }
        LinearLayout productCard = convertView.findViewById(R.id.service_card_item_unavailable);
        TextView productTitle = convertView.findViewById(R.id.product_name);


        if(service != null){
            productTitle.setText(service.getTitle());
            String priceText = String.valueOf(service.getPrice());
            productCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + service.getTitle() + ", id: " +
                        service.getId().toString());
                Toast.makeText(getContext(), "Clicked: " + service.getTitle()  +
                        ", id: " + service.getId().toString(), Toast.LENGTH_SHORT).show();


            });
        }

        return convertView;
    }
    public void setOnServiceSelectedListener(Consumer<Service> listener) {
        mListener = listener;
    }
}
