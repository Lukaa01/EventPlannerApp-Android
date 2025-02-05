package com.example.eventplannerapp.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.eventplannerapp.fragments.vlasnik.ProductsPageFragment;
import com.example.eventplannerapp.model.Product;

import com.example.eventplannerapp.R;

import java.util.ArrayList;
import java.util.function.Consumer;

/*
 * Adapteri unutar Android-a sluze da prikazu unapred nedefinisanu kolicinu podataka
 * pristigle sa interneta ili ucitane iz baze ili filesystem-a uredjaja.
 * Da bi napravili adapter treba da napraivmo klasu, koja nasledjuje neki od postojecih adaptera.
 * Za potrebe ovih vezbi koristicemo ArrayAdapter koji kao izvor podataka iskoristi listu ili niz.
 * Nasledjivanjem bilo kog adaptera, dobicemo
 * nekolko metoda koje moramo da referinisemo da bi adapter ispravno radio.
 * */
public class ProductListAdapter extends ArrayAdapter<Product> {
    private ArrayList<Product> aProducts;
    private Consumer<Product> mListener;
    private NavController navController;


    public ProductListAdapter(Context context, ArrayList<Product> products){
        super(context, R.layout.product_card, products);
        aProducts = products;
    }
    /*
     * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
     * */
    @Override
    public int getCount() {
        return aProducts.size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Nullable
    @Override
    public Product getItem(int position) {
        return aProducts.get(position);
    }

    /*
     * Ova metoda vraca jedinstveni identifikator, za adaptere koji prikazuju
     * listu ili niz, pozicija je dovoljno dobra da bude identifikator.
     * Naravno mozemo iskoristiti i jedinstveni identifikator objekta, ako on postoji.
     * */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * Ova metoda popunjava pojedinacan element ListView-a podacima.
     * Ako adapter cuva listu od n elemenata, adapter ce u petlji ici
     * onoliko puta koliko getCount() vrati. Prilikom svake iteracije
     * uzece java objekat sa odredjene pozicije (model) koji cuva podatke,
     * i layout koji treba da prikaze te podatke (view) npr R.layout.product_card.
     * Kada adapter ima model i view, prosto ce uzeti podatke iz modela,
     * popuniti view podacima i poslati listview da prikaze, i nastavice
     * sledecu iteraciju.
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);
        if(product.isAvailable()) {
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_card,
                        parent, false);
            }
            LinearLayout productCard = convertView.findViewById(R.id.product_card_item);
            TextView productTitle = convertView.findViewById(R.id.product_name);
            TextView productDescription = convertView.findViewById(R.id.product_description);
            TextView productPrice = convertView.findViewById(R.id.product_price);

            if(product != null){
                productTitle.setText(product.getName());
                productDescription.setText(product.getDescription());
                String priceText = String.valueOf(product.getPrice());
                priceText = priceText + " din";
                productPrice.setText(priceText);
                productCard.setOnClickListener(v -> {
                    // Handle click on the item at 'position'
                    Log.i("ShopApp", "Clicked: " + product.getName() + ", id: " +
                            product.getId());
                    Toast.makeText(getContext(), "Clicked: " + product.getName()  +
                            ", id: " + product.getId(), Toast.LENGTH_SHORT).show();
                    if (mListener != null) {
                        mListener.accept(product);
                    }
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

                    // Navigation logic
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("selectedProduct", product);
                    navController.navigate(R.id.nav_product_detail, bundle);

                });
            }

            return convertView;
        }
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_unavailable_card,
                    parent, false);
        }
        LinearLayout productCard = convertView.findViewById(R.id.product_card_item_unavailable);
        TextView productTitle = convertView.findViewById(R.id.product_name);


        if(product != null){
            productTitle.setText(product.getName());
            String priceText = String.valueOf(product.getPrice());
            productCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + product.getName() + ", id: " +
                        product.getId());
                Toast.makeText(getContext(), "Clicked: " + product.getName()  +
                        ", id: " + product.getId(), Toast.LENGTH_SHORT).show();
                if (mListener != null) {
                    mListener.accept(product);
                }


            });
        }

        return convertView;
    }

    public void setOnProductSelectedListener(Consumer<Product> listener) {
        mListener = listener;
    }
}

