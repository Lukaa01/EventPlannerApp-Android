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
import com.example.eventplannerapp.model.Package;

import java.util.ArrayList;

public class PackageListAdapter extends ArrayAdapter<Package> {

    private ArrayList<Package> aPackages;
    private NavController navController;


    public PackageListAdapter(Context context, ArrayList<Package> packages) {
        super(context, R.layout.package_card, packages);
        aPackages = packages;
    }


    @Override
    public int getCount() {
        return aPackages.size();
    }


    @Nullable
    @Override
    public Package getItem(int position) {
        return aPackages.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Package packageItem = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.package_card,
                    parent, false);
        }
        LinearLayout productCard = convertView.findViewById(R.id.package_card_item);
        TextView productTitle = convertView.findViewById(R.id.package_name);
        TextView productDescription = convertView.findViewById(R.id.package_description);
        TextView productPrice = convertView.findViewById(R.id.package_price);

        if(packageItem != null){
            productTitle.setText(packageItem.getTitle());
            productDescription.setText(packageItem.getDescription());
            String priceText = String.valueOf(packageItem.getPrice());
            priceText = priceText + " din";
            productPrice.setText(priceText);
            productCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + packageItem.getTitle() + ", id: " +
                        packageItem.getId().toString());
                Toast.makeText(getContext(), "Clicked: " + packageItem.getTitle()  +
                        ", id: " + packageItem.getId().toString(), Toast.LENGTH_SHORT).show();

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
                bundle.putParcelable("selectedPackage", packageItem);
                navController.navigate(R.id.nav_package_detail, bundle);

            });
        }

        return convertView;
    }
}
