package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.PDFGenerator;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.ProductListAdapter;
import com.example.eventplannerapp.databinding.FragmentPricelistPageBinding;
import com.example.eventplannerapp.databinding.FragmentProductsPageBinding;
import com.example.eventplannerapp.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PricelistPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PricelistPageFragment extends Fragment {

    private static final String TAG = "YourClass";
    public static ArrayList<Product> products = new ArrayList<Product>();
    private FragmentPricelistPageBinding binding;
    private PriceListAdapter pricelistAdapter;
    private NavController navController;
    private Product selectedProduct;
    private PricelistPageViewModel pricelistPageViewModel;
    private ArrayList<Product> allProducts;

    public static PricelistPageFragment newInstance() {
        return new PricelistPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String activityName = getActivity().getClass().getSimpleName();
        Log.d(TAG, activityName);

        pricelistPageViewModel = new ViewModelProvider(this).get(PricelistPageViewModel.class);

        binding = FragmentPricelistPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        products = new ArrayList<>();
        prepareProductList(products);

        SearchView searchView = binding.searchText;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProductssByName(newText);
                return true;
            }
        });

        pricelistPageViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);

        Button btnFilters = binding.buttonFilter;
        btnFilters.setOnClickListener(v -> {
            Log.i("ShopApp", "Bottom Sheet Dialog");
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
            bottomSheetDialog.setContentView(dialogView);
            bottomSheetDialog.show();
        });

        Button btnPDF = binding.pdfButton;
        btnPDF.setOnClickListener(v -> {
            if(!products.isEmpty()) {
                PDFGenerator.exportPriceListToPDF(products, getActivity());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void prepareProductList(ArrayList<Product> products){
        CloudStoreUtil.selectAllProducts().thenAccept(productList -> {
            products.addAll(productList);

            FragmentTransition.to(PriceListFragment.newInstance(products), getActivity(),
                    false, R.id.scroll_pricelist);

            this.allProducts.addAll(products);

        }).exceptionally(exception -> {
            return null;
        });

        Log.i("REZ_DB", "SELECT DATA");
    }
    private void filterProductssByName(String search) {
        ArrayList<Product> productsAll = new ArrayList<Product>();
        CloudStoreUtil.selectAllProducts().thenAccept(productList -> {

            // Ovdje obradite listu proizvoda
            productsAll.addAll(productList);
            // .to metoda je premestena ovde zbog asinhronog izvrsavanja

            ArrayList<Product> filteredProducts = new ArrayList<>();

            for (Product p : productsAll) {
                if (p.getName().toLowerCase().contains(search.toLowerCase())) {
                    filteredProducts.add(p);
                }

            }

            FragmentTransition.to(PriceListFragment.newInstance(filteredProducts), getActivity(),
                    false, R.id.scroll_pricelist);

            //productListAdapter = new ProductListAdapter(getContext(), products);

        }).exceptionally(exception -> {
            // Ovdje rukujte iznimkom
            return null; // ili povratna vrijednost u slučaju iznimke
        });
    }
}