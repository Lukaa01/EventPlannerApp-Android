package com.example.eventplannerapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.Employee;
import com.example.eventplannerapp.model.Service;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetailsFragment extends Fragment {

    private Service selectedService;
    private Employee selectedEmployee;
    private NavController navController;
    private static final String TAG = "ServiceDetailsFragment";

    public ServiceDetailsFragment() {
        // Required empty public constructor
    }

    public static ServiceDetailsFragment newInstance(String param1, String param2) {
        ServiceDetailsFragment fragment = new ServiceDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedService = getArguments().getParcelable("selectedService");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_service_details, container, false);
        Switch switchFavourites = root.findViewById(R.id.switch_favourites);

        // Handle navigation button
        Button navButton = root.findViewById(R.id.button_action);
        navButton.setOnClickListener(v -> {
            navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
            navController.navigate(R.id.nav_vlasnik_profile);
        });

        // Handle reserve button
        MaterialButton reserveButton = root.findViewById(R.id.reserve_button);
        reserveButton.setOnClickListener(view -> {
            if (selectedService != null) {
                fetchEmployees(selectedService.getTitle(), reserveButton);
            }
        });

        // Populate service details
        if (selectedService != null) {
            TextView serviceName = root.findViewById(R.id.service_detail_name);
            TextView serviceDescription = root.findViewById(R.id.service_detail_description);
            TextView servicePrice = root.findViewById(R.id.service_detail_price);
            TextView serviceDuration = root.findViewById(R.id.service_detail_duration);
            TextView serviceCategory = root.findViewById(R.id.service_detail_category);
            TextView serviceSubcategory = root.findViewById(R.id.service_detail_subcategory);
            TextView serviceAvailable = root.findViewById(R.id.service_detail_available);

            serviceName.setText(selectedService.getTitle());
            serviceDescription.setText(selectedService.getDescription());
            servicePrice.setText(String.valueOf(selectedService.getPrice()));
            serviceDuration.setText(String.valueOf(selectedService.getDuration()));
            serviceCategory.setText(selectedService.getCategory().toString());
            serviceSubcategory.setText(selectedService.getSubcategory().toString());
            serviceAvailable.setText(selectedService.isAvailable() ? "Yes" : "No");

            switchFavourites.setChecked(selectedService.isFavourite());


        }

        switchFavourites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update the selectedProduct's favourite field
                selectedService.setFavourite(isChecked);

                // Call CloudStoreUtil.updateProduct() to update the product
                CloudStoreUtil.updateService(selectedService);
            }
        });


        return root;
    }

    private void fetchEmployees(String serviceName, MaterialButton reserveButton) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("employees")
                .whereArrayContains("services", serviceName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Employee> employees = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            Employee employee = document.toObject(Employee.class);
                            employees.add(employee);
                        }
                        showEmployeePopupMenu(employees, reserveButton);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                        Toast.makeText(getContext(), "Failed to get employees.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showEmployeePopupMenu(List<Employee> employees, View anchorView) {
        PopupMenu popup = new PopupMenu(getContext(), anchorView);
        Menu menu = popup.getMenu();

        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            MenuItem menuItem = menu.add(Menu.NONE, i, Menu.NONE, employee.getFirstname());
            menuItem.setOnMenuItemClickListener(item -> {
                handleEmployeeSelection(employee);
                navController = Navigation.findNavController(requireActivity(), R.id.fragment_nav_content_main_org);

                Bundle bundle = new Bundle();
                bundle.putParcelable("selectedService", selectedService);
                bundle.putParcelable("selectedEmployee", selectedEmployee);

                navController.navigate(R.id.action_service_detail_to_service_reservation, bundle);
                return true;
            });
        }

        popup.show();
    }

    private void handleEmployeeSelection(Employee employee) {
        selectedEmployee = employee;
    }
}