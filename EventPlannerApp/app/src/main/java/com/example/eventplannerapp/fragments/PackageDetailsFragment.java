package com.example.eventplannerapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.Package;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PackageDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PackageDetailsFragment extends Fragment {
    private Package selectedPackage;
    private NavController navController;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PackageDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PackageDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PackageDetailsFragment newInstance(String param1, String param2) {
        PackageDetailsFragment fragment = new PackageDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedPackage = getArguments().getParcelable("selectedPackage");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_package_details, container, false);
        Switch switchFavourites = root.findViewById(R.id.switch_favourites);
        Button navButton = root.findViewById(R.id.button_action);


        if (selectedPackage != null) {
            TextView serviceName = root.findViewById(R.id.service_detail_name);
            TextView serviceDescription = root.findViewById(R.id.service_detail_description);
            TextView servicePrice = root.findViewById(R.id.service_detail_price);
            TextView serviceCategory = root.findViewById(R.id.package_detail_category);
            TextView serviceAvailable = root.findViewById(R.id.service_detail_available);

            serviceName.setText(selectedPackage.getTitle());
            serviceDescription.setText(selectedPackage.getDescription());
            servicePrice.setText(String.valueOf(selectedPackage.getPrice()));
            serviceCategory.setText(selectedPackage.getCategory().toString());
            serviceAvailable.setText(selectedPackage.isAvailable() ? "Yes" : "No");


        }

        navButton.setOnClickListener(v -> {
            navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main_org);
            navController.navigate(R.id.nav_vlasnik_profile);
        });

        return root;
    }
}