package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.ProductListAdapter;
import com.example.eventplannerapp.activities.adapters.ServiceListAdapter;
import com.example.eventplannerapp.databinding.FragmentServicePageBinding;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Service;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServicePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServicePageFragment extends Fragment {

    public static ArrayList<Service> services = new ArrayList<>();
    private ServicePageViewModel serviceViewModel;
    private FragmentServicePageBinding binding;
    private NavController navController;
    private ServiceListAdapter serviceListAdapter;
    private Service selectedService;


    private ArrayList<Service> allServices = new ArrayList<>();

    //private boolean isFirstSelection = true;

    public static ServicePageFragment newInstance() {
        return new ServicePageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        serviceViewModel = new ViewModelProvider(this).get(ServicePageViewModel.class);

        binding = FragmentServicePageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        services = new ArrayList<>();
        prepareServiceList(services);

        ServiceListAdapter adapter = new ServiceListAdapter(getContext(), allServices);
        adapter.setOnServiceSelectedListener(service -> {
            selectedService = service;
            Log.i("ShopApp", "Selektovani product: " + selectedService.getTitle());
        });

        //SearchView searchView = binding.searchTextServices;

       // serviceViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);

        Button btnFilters = binding.btnServiceFilters;
        btnFilters.setOnClickListener(v -> {
            Log.i("ShopApp", "Bottom Sheet Dialog");
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
            bottomSheetDialog.setContentView(dialogView);
            bottomSheetDialog.show();
        });

        FloatingActionButton floatingAddBtn = binding.floatingAddServiceButton;
        floatingAddBtn.setOnClickListener(v -> {
            navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
            navController.navigate(R.id.nav_create_service);
        });

        FloatingActionButton floatingEditBtn = binding.floatingEditServiceButton;
        floatingEditBtn.setOnClickListener(v -> {
            // Implement edit functionality if needed
        });
        FloatingActionButton floatingDeleteBtn = binding.floatingDeleteServiceButton;
        floatingDeleteBtn.setOnClickListener(v -> {
            // Implement delete functionality if needed
        });

        String activityName = getActivity().getClass().getSimpleName();

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

        return root;
    }

    private void fetchServicesFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("services").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Service service = document.toObject(Service.class);
                            services.add(service);
                        }
                        updateUIWithServices();
                    } else {
                        Log.w("ServicePageFragment", "Error getting documents.", task.getException());
                    }
                });
    }

    private void updateUIWithServices() {
        FragmentTransition.to(ServiceListFragment.newInstance(services), getActivity(),
                false, R.id.scroll_services_list);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void prepareServiceList(ArrayList<Service> services){
        if(services == null) {
            services = new ArrayList<>();
        }
        services.clear();

        ArrayList<Service> finalServices = services;

        CloudStoreUtil.selectVisibleServices().thenAccept(list -> {
            if(list != null) {
                finalServices.addAll(list);
                FragmentTransition.to(ServiceListFragment.newInstance(finalServices), getActivity(),
                        false, R.id.scroll_services_list);
            }

        }).exceptionally(exception -> {
            return null;
        });

        Log.i("REZ_DB", "SELECT DATA");
    }
}