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

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.EventType;
import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.FragmentPackagePageBinding;
import com.example.eventplannerapp.model.Category;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Package;
import com.example.eventplannerapp.model.Service;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PackagePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PackagePageFragment extends Fragment {

    public static ArrayList<Package> packages = new ArrayList<>();
    private PackagePageViewModel packageViewModel;
    private FragmentPackagePageBinding binding;
    private NavController navController;
    //private boolean isFirstSelection = true;

    public static ServicePageFragment newInstance() {
        return new ServicePageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        packageViewModel = new ViewModelProvider(this).get(PackagePageViewModel.class);

        binding = FragmentPackagePageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        packages = new ArrayList<>();
        prepareProductList(packages);

        SearchView searchView = binding.searchTextPackages;

        packageViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);

        Button btnFilters = binding.btnPackageFilters;
        btnFilters.setOnClickListener(v -> {
            Log.i("ShopApp", "Bottom Sheet Dialog");
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
            bottomSheetDialog.setContentView(dialogView);
            bottomSheetDialog.show();
        });

        FloatingActionButton floatingAddBtn = binding.floatingAddPackageButton;
        floatingAddBtn.setOnClickListener(v -> {
            navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
            navController.navigate(R.id.nav_create_package);
        });

        FloatingActionButton floatingEditBtn = binding.floatingEditPackageButton;
        floatingEditBtn.setOnClickListener(v -> {
            /*navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
            navController.navigate(R.id.nav_create_product);*/
        });
        FloatingActionButton floatingDeleteBtn = binding.floatingDeletePackageButton;
        floatingDeleteBtn.setOnClickListener(v -> {
            /*navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
            navController.navigate(R.id.nav_create_product);*/
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

        FragmentTransition.to(PackageListFragment.newInstance(packages), getActivity(),
                false, R.id.scroll_packages_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void prepareProductList(ArrayList<Package> packages){
        /*
        if (packages == null) {
            packages = new ArrayList<>();
        }
        packages.clear();

        ArrayList<Package> finalPackages = packages;

        CloudStoreUtil.selectAllPackages().thenAccept(list-> {
            if (list != null) {
                finalPackages.addAll(list);
                FragmentTransition.to(PackageListFragment.newInstance(finalPackages), getActivity(),
                        false, R.id.scroll_packages_list);
            }
        }).exceptionally(exception -> {
            Log.e("REZ_DB", "Error fetching products", exception);
            return null;
        });
        */
        // Kreiranje i dodavanje prvog paketa
        Package package1 = new Package();
        package1.setId("wasd");
        package1.setTitle("Standard Package");
        package1.setDescription("Standard package description");
        package1.setCategory("Category");
        package1.setPrice(100);
        package1.setDiscount(0.1); // 10% discount
        package1.setProducts(Arrays.asList(
                new Product("1", "Category1", "Subcategory1", "Product1", "Product description 1", 50, 5, 45, Arrays.asList("image1.jpg"), "EventType1", true, true, true),
                new Product("2", "Category2", "Subcategory2", "Product2", "Product description 2", 60, 10, 50, Arrays.asList("image2.jpg"), "EventType2", true, true, true)
        ));
        package1.setServices(Arrays.asList(
                new Service("1", "Service1", "Service description 1", "Category1", "Subcategory1", 80, 8, 72, 2.5, "Location1", Arrays.asList("InCharge1", "InCharge2"), "Specifics1", Arrays.asList("EventType1", "EventType2"), Arrays.asList("image3.jpg"), "2024-12-31", "2024-12-15", true, true, true, 2.0, 3.0, true),
                new Service("2", "Service2", "Service description 2", "Category2", "Subcategory2", 90, 9, 81, 3.0, "Location2", Arrays.asList("InCharge3", "InCharge4"), "Specifics2", Arrays.asList("EventType1", "EventType3"), Arrays.asList("image4.jpg"), "2024-12-31", "2024-12-15", true, true, true, 2.5, 3.5, true)
        ));
        package1.setImages(Arrays.asList("image1.jpg", "image2.jpg"));
        package1.setReservationDeadline("2024-12-31");
        package1.setCancellationDeadline("2024-12-15");
        package1.setAutomaticAccepted(true);
        package1.setVisible(true);
        package1.setAvailable(true);

        // Dodavanje prvog paketa u listu
        packages.add(package1);
    }
}