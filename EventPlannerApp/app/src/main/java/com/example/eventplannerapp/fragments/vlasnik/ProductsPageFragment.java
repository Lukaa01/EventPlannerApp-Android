package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.ProductListAdapter;
import com.example.eventplannerapp.databinding.FragmentProductsPageBinding;
import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsPageFragment extends Fragment {
    private static final String TAG = "YourClass";
    public static ArrayList<Product> products = new ArrayList<Product>();
    private ProductsPageViewModel productsViewModel;
    private FragmentProductsPageBinding binding;
    private ProductListAdapter productListAdapter;
    private NavController navController;
    private Product selectedProduct;
    private ArrayList<Product> allProducts = new ArrayList<>();

    //private boolean isFirstSelection = true;

    public static ProductsPageFragment newInstance() {
        return new ProductsPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String activityName = getActivity().getClass().getSimpleName();
        Log.d(TAG, activityName);

        // Instanciranje ViewModel-a
        // ViewModelProvider osigurava da se ViewModel ne stvara svaki
        // put prilikom promjene konfiguracije (npr. rotacije ekrana),
        // već se ponovno koristi postojeća instanca, čime se očuvaju podaci.
        productsViewModel = new ViewModelProvider(this).get(ProductsPageViewModel.class);

        binding = FragmentProductsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        products = new ArrayList<>();
        prepareProductList(products);

        ProductListAdapter adapter = new ProductListAdapter(getContext(), allProducts);
        adapter.setOnProductSelectedListener(product -> {
            selectedProduct = product;
            Log.i("ShopApp", "Selektovani product: " + selectedProduct.getName());
        });
/*
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
        });*/
        /* Posmatranje LiveData objekta i ažuriranje UI-a:
         * Ovaj deo koda dodaje observer na LiveData<String> objekat unutar
         * ProductsPageViewModel. Svaki put kada dođe do promene podatka
         * unutar LiveData objekta (u ovom slučaju searchText),
         * ta promena se automatski prosleđuje i aktivira se metoda
         * setQueryHint na SearchView komponenti sa novom vrednošću.
         * Funkcija getViewLifecycleOwner() garantuje da se observer
         * povezuje sa životnim ciklusom vlasnika prikaza fragmenta,
         * što znači da će se observer automatski ukloniti kada fragment
         * više nije vidljiv ili je uništen, sprečavajući time moguće curenje memorije.
         * */
        //productsViewModel.getText().observe(getViewLifecycleOwner());

        Button btnFilters = binding.btnFilters;
        btnFilters.setOnClickListener(v -> {
            Log.i("ShopApp", "Bottom Sheet Dialog");
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
            bottomSheetDialog.setContentView(dialogView);
            bottomSheetDialog.show();
        });

        FloatingActionButton floatingAddBtn = binding.floatingAddButton;
        floatingAddBtn.setOnClickListener(v -> {
            navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
            navController.navigate(R.id.nav_create_product);
        });

        FloatingActionButton floatingEditBtn = binding.floatingEditButton;
        floatingEditBtn.setOnClickListener(v -> {
            navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
            navController.navigate(R.id.nav_create_product);
        });

        FloatingActionButton floatingDeleteBtn = binding.floatingDeleteButton;
        floatingDeleteBtn.setOnClickListener(v -> {
            if(selectedProduct != null){
                CloudStoreUtil.deleteProduct(selectedProduct.getId());
            } else {
                Toast.makeText(getContext(), "Select product for deletion!", Toast.LENGTH_SHORT).show();
            }
        });
        if (activityName.equals("VlasnikHomeActivity")) {
            // The activity name is "MainActivity"
            floatingAddBtn.setVisibility(View.VISIBLE);
            floatingDeleteBtn.setVisibility(View.VISIBLE);
            floatingEditBtn.setVisibility(View.VISIBLE);
        } else {
            // The activity name is not "MainActivity"
            floatingAddBtn.setVisibility(View.INVISIBLE);
            floatingDeleteBtn.setVisibility(View.INVISIBLE);
            floatingEditBtn.setVisibility(View.INVISIBLE);
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity());
        // Specify the layout to use when the list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Provera da li je ovo prvi poziv
                if (isFirstSelection) {
                    isFirstSelection = false;
                    return; // Izlazak iz metode bez prikazivanja dijaloga
                }
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setMessage("Change the sort option?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.v("ShopApp", (String) parent.getItemAtPosition(position));
                                ((TextView) parent.getChildAt(0)).setTextColor(Color.MAGENTA);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = dialog.create();
                alert.show();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });*/


        /*FragmentTransition.to(ProductListFragment.newInstance(products), getActivity(),
                false, R.id.scroll_products_list);*/

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void prepareProductList(ArrayList<Product> products) {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.clear();

        // Use an effectively final wrapper to hold the products list
        ArrayList<Product> finalProducts = products;

        CloudStoreUtil.selectVisibleProducts().thenAccept(productList -> {
            if (productList != null) {
                finalProducts.addAll(productList);
                FragmentTransition.to(ProductListFragment.newInstance(finalProducts), getActivity(),
                        false, R.id.scroll_products_list);
            }
        }).exceptionally(exception -> {
            Log.e("REZ_DB", "Error fetching products", exception);
            return null;
        });
    }



/*
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

            FragmentTransition.to(ProductListFragment.newInstance(filteredProducts), getActivity(),
                    false, R.id.scroll_products_list);

            //productListAdapter = new ProductListAdapter(getContext(), products);

        }).exceptionally(exception -> {
            // Ovdje rukujte iznimkom
            return null; // ili povratna vrijednost u slučaju iznimke
        });


    }*/

}