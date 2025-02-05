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

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PriceListAdapter extends ArrayAdapter<Product> {

    private final ArrayList<Product> aProducts;
    private Context context;
    private FragmentPriceListBinding binding;
    public PriceListAdapter(Context c, ArrayList<Product> products) {
        super(c, R.layout.fragment_price_list, products);
        context = c;
        aProducts = products;
    }
    @Override
    public int getCount() {
        return aProducts.size();
    }

    @Nullable
    @Override
    public Product getItem(int position) {
        return aProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_price_list,
                    parent, false);
        }
        LinearLayout productCard = convertView.findViewById(R.id.pricelist_item);
        TextView productTitle = convertView.findViewById(R.id.pricelist_name);
        TextView productPrice = convertView.findViewById(R.id.pricelist_price);
        TextView discount = convertView.findViewById(R.id.pricelist_discount);
        TextView discountedPrice = convertView.findViewById(R.id.pricelist_price_with_discount);
        Button btn = convertView.findViewById(R.id.change_price_button);

        if(product != null){
            productTitle.setText(product.getName());
            String priceText = String.valueOf(product.getPrice());
            priceText = priceText + " din";
            String discountText = String.valueOf(product.getDiscount());
            discountText = discountText + " %";
            String discountedPriceText = String.valueOf(product.getDiscountedPrice());
            discountedPriceText = discountedPriceText + " din";
            productPrice.setText(priceText);
            discount.setText(discountText);
            discountedPrice.setText(discountedPriceText);
            View finalConvertView = convertView;
            btn.setOnClickListener(c -> {
                NavController navController = Navigation.findNavController(finalConvertView);
                Bundle bundle = new Bundle();
                bundle.putParcelable("product", product);
                navController.navigate(R.id.nav_update_pricelist, bundle);
            });
        }

        return convertView;
    }

}