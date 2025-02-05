package com.example.eventplannerapp.fragments.organizator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.FragmentBudgetPlanBinding;
import com.example.eventplannerapp.databinding.FragmentFavouritesListBinding;
import com.example.eventplannerapp.fragments.vlasnik.ProductListFragment;
import com.example.eventplannerapp.fragments.vlasnik.ProductsPageViewModel;
import com.example.eventplannerapp.fragments.vlasnik.ServiceListFragment;
import com.example.eventplannerapp.fragments.vlasnik.ServicePageViewModel;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouritesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouritesListFragment extends Fragment {

    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<Service> services = new ArrayList<>();
    private ArrayList<Product> selectedProducts = new ArrayList<>();
    private ArrayList<Service> selectedServices = new ArrayList<>();
    private ServicePageViewModel servicePageViewModel;
    private ProductsPageViewModel productsPageViewModel;
    private FragmentFavouritesListBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavouritesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouritesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouritesListFragment newInstance(String param1, String param2) {
        FavouritesListFragment fragment = new FavouritesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        productsPageViewModel = new ViewModelProvider(this).get(ProductsPageViewModel.class);
        servicePageViewModel = new ViewModelProvider(this).get(ServicePageViewModel.class);
        binding = FragmentFavouritesListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        products = new ArrayList<>();
        prepareServiceList(services);
        prepareProductList(products);

        binding.buttonFavouriteProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                FragmentTransition.to(ProductListFragment.newInstance(products), getActivity(),
                        false, R.id.downView);*/
                products = new ArrayList<>();
                prepareProductList(products);
            }
        });

        binding.buttonFavouriteServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                services = new ArrayList<>();
                prepareServiceList(services);
            }
        });

        return root;
    }
    private void prepareProductList(ArrayList<Product> products) {
        CloudStoreUtil.selectAllProducts().thenAccept(productList -> {
            // Filtriranje proizvoda koji imaju favorite polje postavljeno na true
            List<Product> filteredProducts = productList.stream()
                    .filter(Product::isFavourite)
                    .collect(Collectors.toList());

            // Prikazivanje filtrirane liste proizvoda
            FragmentTransition.to(ProductListFragment.newInstance(new ArrayList<>(filteredProducts)), getActivity(),
                    false, R.id.downView);
        }).exceptionally(exception -> {
            // Ovdje rukujte iznimkom
            return null; // ili povratna vrijednost u slučaju iznimke
        });
    }

    private void prepareServiceList(ArrayList<Service> services) {
        CloudStoreUtil.selectAllServices().thenAccept(serviceList -> {
            // Filtriranje usluga koje imaju favorite polje postavljeno na true
            List<Service> filteredServices = serviceList.stream()
                    .filter(Service::isFavourite)
                    .collect(Collectors.toList());

            // Prikazivanje filtrirane liste usluga
            FragmentTransition.to(ServiceListFragment.newInstance(new ArrayList<>(filteredServices)), getActivity(),
                    false, R.id.downView);
        }).exceptionally(exception -> {
            // Ovdje rukujte iznimkom
            return null; // ili povratna vrijednost u slučaju iznimke
        });
    }

}