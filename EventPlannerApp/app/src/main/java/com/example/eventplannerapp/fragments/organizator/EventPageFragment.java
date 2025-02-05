package com.example.eventplannerapp.fragments.organizator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.FragmentEventPageBinding;
import com.example.eventplannerapp.fragments.vlasnik.ProductListFragment;
import com.example.eventplannerapp.fragments.vlasnik.ServicePageFragment;
import com.example.eventplannerapp.model.Event1;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventPageFragment extends Fragment {

    public static ArrayList<Event1> events = new ArrayList<Event1>();
    private EventPageViewModel eventPageViewModel;
    private FragmentEventPageBinding binding;
    private NavController navController;

    public static EventPageFragment newInstance() {
        return new EventPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        eventPageViewModel = new ViewModelProvider(this).get(EventPageViewModel.class);
        binding = FragmentEventPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        events = new ArrayList<>();
        prepareEventList(events);

        SearchView searchView = binding.searchTextEvents;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterEventsByName(newText);
                return true;
            }
        });

        eventPageViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);
        Button btnFilters = binding.btnEventFilters;
        btnFilters.setOnClickListener(v -> {
            Log.i("ShopApp", "Bottom Sheet Dialog");
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
            bottomSheetDialog.setContentView(dialogView);
            bottomSheetDialog.show();
        });
/*
        FragmentTransition.to(EventListFragment.newInstance(events), getActivity(),
                false, R.id.scroll_events_list);*/

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void prepareEventList(ArrayList<Event1> events) {
        CloudStoreUtil.selectAllEvents().thenAccept(eventList -> {
            // Ovdje obradite listu proizvoda
            events.addAll(eventList);
            // Log all events
            for (Event1 event : eventList) {
                Log.d("EventPageFragment", "Event: " + event.toString());
            }
            // .to metoda je premestena ovde zbog asinhronog izvrsavanja
            getActivity().runOnUiThread(() -> {
                FragmentTransition.to(EventListFragment.newInstance(events), getActivity(),
                        false, R.id.scroll_events_list);
            });

            //productListAdapter = new ProductListAdapter(getContext(), products);

        }).exceptionally(exception -> {
            // Ovdje rukujte iznimkom
            return null; // ili povratna vrijednost u slučaju iznimke
        });
    }


    private void filterEventsByName(String search) {
        ArrayList<Event1> eventsAll = new ArrayList<Event1>();
        CloudStoreUtil.selectAllEvents().thenAccept(eventList -> {

            // Ovdje obradite listu proizvoda
            eventsAll.addAll(eventList);
            // .to metoda je premestena ovde zbog asinhronog izvrsavanja

            ArrayList<Event1> filteredEvents = new ArrayList<>();

            for (Event1 event : eventsAll) {
                if (event.getName().toLowerCase().contains(search.toLowerCase())) {
                    filteredEvents.add(event);
                }
                else if (event.getEventType().toLowerCase().contains(search.toLowerCase())) {
                    filteredEvents.add(event);
                }
                else if (event.getLocation().toLowerCase().contains(search.toLowerCase())) {
                    filteredEvents.add(event);
                }
                else if (event.getDate().toLowerCase().contains(search.toLowerCase())) {
                    filteredEvents.add(event);
                }
            }

            FragmentTransition.to(EventListFragment.newInstance(filteredEvents), getActivity(),
                    false, R.id.scroll_events_list);

            //productListAdapter = new ProductListAdapter(getContext(), products);

        }).exceptionally(exception -> {
            // Ovdje rukujte iznimkom
            return null; // ili povratna vrijednost u slučaju iznimke
        });


    }


}