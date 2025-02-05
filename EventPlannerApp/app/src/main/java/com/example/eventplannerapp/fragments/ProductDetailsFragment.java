package com.example.eventplannerapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.EventOrganizer;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.ProductReservation;
import com.example.eventplannerapp.model.ReservationStatus;
import com.example.eventplannerapp.model.ServiceReservation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {
    private Product selectedProduct;

    private NavController navController;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailsFragment newInstance(String param1, String param2) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String activityName = getActivity().getClass().getSimpleName();


        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        Switch switchFavourites = view.findViewById(R.id.switch_favourites);

         Button navButton = view.findViewById(R.id.button_action);
        MaterialButton purchaseButton = view.findViewById(R.id.purchase_button);



         navButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (activityName.equals("VlasnikHomeActivity")) {
                     navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
                 }
                 else {
                     navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main_org);

                 }
                 navController.navigate(R.id.nav_vlasnik_profile);
             }
         });

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments() != null) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    ProductReservation productReservation = new ProductReservation();
                    CollectionReference serviceReservationsRef = db.collection("event_organizers");

                    serviceReservationsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    EventOrganizer organizer = documentSnapshot.toObject(EventOrganizer.class);
                                    Log.d("organizer", organizer.getEmail());
                                    productReservation.setEmail(organizer.getEmail());
                                    break;
                                }

                                // Ensure the selectedProduct is set only after email has been fetched
                                selectedProduct = getArguments().getParcelable("selectedProduct");
                                productReservation.setProduct(selectedProduct);
                                productReservation.setReservationStatus(ReservationStatus.NEW);
                                productReservation.setId(UUID.randomUUID().toString());

                                db.collection("productReservations").document(selectedProduct.getId()).set(productReservation).addOnCompleteListener(
                                        task -> {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Reservation made", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getContext(), "Failed to make reservation", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                );
                            } else {
                                Toast.makeText(getContext(), "No organizers found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to fetch organizers", Toast.LENGTH_SHORT).show();
                        Log.e("FirestoreError", "Error fetching organizers", e);
                    });
                }
            }
        });


        if (getArguments() != null) {
            selectedProduct = getArguments().getParcelable("selectedProduct");
            TextView productName = view.findViewById(R.id.product_detail_name);
            TextView productDescription = view.findViewById(R.id.product_detail_description);
            TextView productPrice = view.findViewById(R.id.product_detail_price);
            TextView productCategory = view.findViewById(R.id.product_detail_category);
            TextView productSubcategory = view.findViewById(R.id.product_detail_subcategory);
            TextView productAvailable = view.findViewById(R.id.product_detail_available);

            productName.setText(selectedProduct.getName());
            productDescription.setText(selectedProduct.getDescription());
            productPrice.setText(String.valueOf(selectedProduct.getPrice()));
            productCategory.setText(selectedProduct.getCategory());
            productSubcategory.setText(selectedProduct.getSubcategory());
            productAvailable.setText(selectedProduct.isAvailable() ? "Yes" : "No");
            boolean isFavourite = selectedProduct.isFavourite();
            Log.d("ProductDetailsFragment", "Product isFavourite: " + isFavourite);
            switchFavourites.setChecked(selectedProduct.isFavourite());


        }

        // Set OnCheckedChangeListener to detect when the Switch state changes
        switchFavourites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update the selectedProduct's favourite field
                selectedProduct.setFavourite(isChecked);

                // Call CloudStoreUtil.updateProduct() to update the product
                CloudStoreUtil.updateProduct(selectedProduct);
            }
        });


        return view;

    }
}