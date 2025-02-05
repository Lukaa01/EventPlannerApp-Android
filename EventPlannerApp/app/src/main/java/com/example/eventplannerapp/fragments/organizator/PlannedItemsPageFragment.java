package com.example.eventplannerapp.fragments.organizator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.EventType;
import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.Subcategory;
import com.example.eventplannerapp.activities.adapters.SubcategoryAdapter;
import com.example.eventplannerapp.databinding.FragmentPlannedItemsPageBinding;
import com.example.eventplannerapp.model.BudgetItem;
import com.example.eventplannerapp.model.Event1;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlannedItemsPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlannedItemsPageFragment extends Fragment {

    public static ArrayList<BudgetItem> items = new ArrayList<>();
    private PlannedItemsPageViewModel plannedItemsPageViewModel;
    private FragmentPlannedItemsPageBinding binding;
    private NavController navController;
    private EditText editTextPrice;
    public String eventId;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlannedItemsPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlannedItemsPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlannedItemsPageFragment newInstance(String param1, String param2) {
        return new PlannedItemsPageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Use the event ID as needed
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_planned_items_page, container, false);

        plannedItemsPageViewModel = new ViewModelProvider(this).get(PlannedItemsPageViewModel.class);
        binding = FragmentPlannedItemsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textViewTotalPrice = view.findViewById(R.id.textViewPriceValue);


        items = new ArrayList<>();
        prepareBudgetItems(items, textViewTotalPrice);
/*
        FragmentTransition.to(PlannedItemListFragment.newInstance(items), getActivity(),
                false, R.id.scroll_budget_list);*/
        Button createEventButton = view.findViewById(R.id.buttonAdd);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showCustomDialog(textViewTotalPrice);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareBudgetItems(ArrayList<BudgetItem> items, TextView price) {
        /*
        BudgetItem budgetItem1 = new BudgetItem("wasd", "Food", 100.0, "1L");
        BudgetItem budgetItem2 = new BudgetItem("www", "Venue", 500.0,"2L" );
        BudgetItem budgetItem3 = new BudgetItem("qwerty", "Decorations", 200.0, "3L");

        items.add(budgetItem1);
        items.add(budgetItem2);
        items.add(budgetItem3);*/

        CloudStoreUtil.selectAllBudgetItems().thenAccept(eventList -> {
            // Ovdje obradite listu proizvoda
            items.addAll(eventList);
            // Log all events
            for (BudgetItem event : eventList) {
                Log.d("EventPageFragment", "Event: " + event.toString());
            }
            // .to metoda je premestena ovde zbog asinhronog izvrsavanja
            getActivity().runOnUiThread(() -> {
                FragmentTransition.to(PlannedItemListFragment.newInstance(items), getActivity(),
                        false, R.id.scroll_budget_list);
            });

            updateTotalPrice(price, items); // Move this line here

        }).exceptionally(exception -> {
            // Ovdje rukujte iznimkom
            return null; // ili povratna vrijednost u slučaju iznimke
        });



    }

    private void showCustomDialog(TextView price) {


        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_budget_item, null);

        Spinner spinnerSubcategory = dialogView.findViewById(R.id.spinnerSubcategory);
        EditText editTextAmount = dialogView.findViewById(R.id.editTextPrice);

        Subcategory[] subcategories = Subcategory.values();
        SubcategoryAdapter adapter = new SubcategoryAdapter(requireContext(), subcategories);
        spinnerSubcategory.setAdapter(adapter);



        builder.setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Here you can retrieve the entered values
                        String name = spinnerSubcategory.getSelectedItem().toString();
                        String amount = editTextAmount.getText().toString();

                        // Perform any actions with the entered data

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
        spinnerSubcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Subcategory selected = (Subcategory) parent.getItemAtPosition(position);
                // You can use selectedEventType here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        editTextPrice = dialogView.findViewById(R.id.editTextPrice);

        Button addBudgetItem = dialogView.findViewById(R.id.buttonConfirm);
        addBudgetItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numGuestsStr = editTextPrice.getText().toString().trim();
                double numGuests;
                numGuests = Double.parseDouble(numGuestsStr);
                Subcategory selected = (Subcategory) spinnerSubcategory.getSelectedItem();

                BudgetItem b = new BudgetItem("", selected.toString(), numGuests,"ewkoerkprw");
                CloudStoreUtil.insertBudgetItem(b);
                CloudStoreUtil.selectAllBudgetItems().thenAccept(updatedList -> {
                    items.clear(); // Clear the existing list
                    items.addAll(updatedList); // Update with the new list
                    updateTotalPrice(price, items); // Update total price
                    // Update the UI if needed
                    // Notify adapters or refresh views
                });

            }
        });





    }

    private void updateTotalPrice(TextView price, ArrayList<BudgetItem> items) {

        double totalPrice = 0.0;
        for (BudgetItem item : items) {
            totalPrice += item.getPrice();
        }

        price.setText(String.valueOf(totalPrice));
    }



}