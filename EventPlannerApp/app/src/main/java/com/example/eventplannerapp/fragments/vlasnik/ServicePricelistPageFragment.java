package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.PDFGenerator;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.FragmentPricelistPageBinding;
import com.example.eventplannerapp.databinding.FragmentServicePricelistPageBinding;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Service;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
public class ServicePricelistPageFragment extends Fragment {

    private ServicePricelistViewModel servicePricelistViewModel;
    private FragmentServicePricelistPageBinding binding;
    private ArrayList<Service> services;

    public static ServicePricelistPageFragment newInstance() {
        return new ServicePricelistPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        servicePricelistViewModel = new ViewModelProvider(this).get(ServicePricelistViewModel.class);

        binding = FragmentServicePricelistPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        services = new ArrayList<>();
        prepareServiceList(services);

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

        servicePricelistViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);

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
            if(!services.isEmpty()) {
                PDFGenerator.exportServicePriceListToPDF(services, getActivity());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void prepareServiceList(ArrayList<Service> services){
        CloudStoreUtil.selectAllServices().thenAccept(serviceList -> {
            services.addAll(serviceList);

            FragmentTransition.to(ServicePricelistFragment.newInstance(services), getActivity(),
                    false, R.id.scroll_service_pricelist);

        }).exceptionally(exception -> {
            return null;
        });

        Log.i("REZ_DB", "SELECT DATA");
    }
    private void filterProductssByName(String search) {
        ArrayList<Service> servicesAll = new ArrayList<>();
        CloudStoreUtil.selectAllServices().thenAccept(serviceList -> {

            servicesAll.addAll(serviceList);

            ArrayList<Service> filteredServices = new ArrayList<>();

            for (Service s : servicesAll) {
                if (s.getTitle().toLowerCase().contains(search.toLowerCase())) {
                    filteredServices.add(s);
                }

            }

            FragmentTransition.to(ServicePricelistFragment.newInstance(filteredServices), getActivity(),
                    false, R.id.scroll_service_pricelist);

        }).exceptionally(exception -> {
            return null;
        });
    }
}