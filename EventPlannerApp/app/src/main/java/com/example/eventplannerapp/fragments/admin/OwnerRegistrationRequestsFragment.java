// OwnerRegistrationRequestsFragment.java
package com.example.eventplannerapp.fragments.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.OwnerAdapter;
import com.example.eventplannerapp.model.Category;
import com.example.eventplannerapp.model.EventType;
import com.example.eventplannerapp.model.Owner;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class OwnerRegistrationRequestsFragment extends Fragment {
    private static final String ARG_PARAM = "owners_param";
    private ListView listView;
    private OwnerAdapter adapter;
    private List<Owner> ownerList;
    private SearchView searchView;
    private PopupMenu popup;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_registration_requests, container, false);
        listView = view.findViewById(R.id.requests_recycler_view);
        ownerList = new ArrayList<>();
        adapter = new OwnerAdapter(getContext(), ownerList);
        listView.setAdapter(adapter);
        searchView = view.findViewById(R.id.search_text_requests);

        if (getArguments() != null && getArguments().containsKey(ARG_PARAM)) {
            ownerList.addAll(getArguments().<Owner>getParcelableArrayList(ARG_PARAM));
            adapter.notifyDataSetChanged();
        } else {
            fetchOwnersFromFirebase();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        MaterialButton filterButton = view.findViewById(R.id.filter_button);
        popup = new PopupMenu(getContext(), filterButton);
        MenuInflater inflater_popup = popup.getMenuInflater();
        inflater_popup.inflate(R.menu.owner_registration_menu, popup.getMenu());

        fetchCategoriesFromFirebase();
        fetchEventTypesFromFirebase();
        //TODO reset filters and oldest and latest
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        item.setChecked(!item.isChecked());
                        return true;
                    }
                });
                popup.show();
            }
        });

        return view;
    }

    private void fetchOwnersFromFirebase() {
        CloudStoreUtil.selectAllOwners().thenAccept(owners -> {
            ownerList.clear();
            ownerList.addAll(owners);
            adapter.notifyDataSetChanged();
        }).exceptionally(exception -> {
            Log.d("OwnerRegistrationRequestsFragment", "Error getting documents: ", exception);
            return null;
        });
    }

    private void filter(String searchText) {
        CloudStoreUtil.selectAllOwners().thenAccept(owners -> {
            ArrayList<Owner> filteredOwners = new ArrayList<>();
            for (Owner item : owners) {
                if (item.getCompanyName().toLowerCase().contains(searchText.toLowerCase()) ||
                        item.getFirstname().toLowerCase().contains(searchText.toLowerCase()) ||
                        item.getLastname().toLowerCase().contains(searchText.toLowerCase()) ||
                        item.getEmail().toLowerCase().contains(searchText.toLowerCase()) ||
                        item.getCompanyEmail().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredOwners.add(item);
                    Log.d("filter", filteredOwners.toString());
                }
            }
            getActivity().runOnUiThread(() -> updateListView(filteredOwners));
        }).exceptionally(exception -> {
            Log.d("OwnerRegistrationRequestsFragment", "Error filtering documents: ", exception);
            return null;
        });
    }

    private void updateListView(List<Owner> filteredOwners) {
        adapter.filterList(filteredOwners);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    private void fetchCategoriesFromFirebase() {
        CloudStoreUtil.selectAllCategories().thenAccept(categories -> {
            List<String> categoriesArray = new ArrayList<>();
            for (Category category : categories){
                categoriesArray.add(category.getName());
            }
            populateMenuWithCategories(categoriesArray);
        }).exceptionally(exception -> {
            Log.d("OwnerRegistrationRequestsFragment", "Error getting categories: ", exception);
            return null;
        });
    }

    private void fetchEventTypesFromFirebase() {
        CloudStoreUtil.selectAllEventTypes().thenAccept(eventTypes -> {
            List<String> eventsArray = new ArrayList<>();
            for (EventType events : eventTypes){
                eventsArray.add(events.getName());
            }
            populateMenuWithEventTypes(eventsArray);
        }).exceptionally(exception -> {
            Log.d("OwnerRegistrationRequestsFragment", "Error getting event types: ", exception);
            return null;
        });
    }

    private void populateMenuWithCategories(List<String> categories) {
        Menu categoryMenu = popup.getMenu().findItem(R.id.filter_by_category).getSubMenu();
        for (String category : categories) {
            categoryMenu.add(Menu.NONE, Menu.NONE, Menu.NONE, category)
                    .setOnMenuItemClickListener(item -> {
                        filterByCategory(category);
                        return true;
                    });
        }
    }

    private void populateMenuWithEventTypes(List<String> eventTypes) {
        Menu eventTypeMenu = popup.getMenu().findItem(R.id.filter_by_event_type).getSubMenu();
        for (String eventType : eventTypes) {
            eventTypeMenu.add(Menu.NONE, Menu.NONE, Menu.NONE, eventType)
                    .setOnMenuItemClickListener(item -> {
                        filterByEventType(eventType);
                        return true;
                    });
        }
    }

    private void filterByCategory(String category) {
        CloudStoreUtil.selectAllOwners().thenAccept(owners -> {
            ArrayList<Owner> filteredOwners = new ArrayList<>();
            for (Owner item : owners) {
                for (String c: item.getCategories()) {
                    if (c.equalsIgnoreCase(category)) {
                        filteredOwners.add(item);
                    }
                }
            }
            getActivity().runOnUiThread(() -> updateListView(filteredOwners));
        }).exceptionally(exception -> {
            Log.d("OwnerRegistrationRequestsFragment", "Error filtering documents: ", exception);
            return null;
        });
    }

    private void filterByEventType(String eventType) {
        CloudStoreUtil.selectAllOwners().thenAccept(owners -> {
            ArrayList<Owner> filteredOwners = new ArrayList<>();
            for (Owner item : owners) {
                for (String c: item.getEventTypes()) {
                    if (c.equalsIgnoreCase(eventType)) {
                        filteredOwners.add(item);
                    }
                }
            }
            getActivity().runOnUiThread(() -> updateListView(filteredOwners));
        }).exceptionally(exception -> {
            Log.d("OwnerRegistrationRequestsFragment", "Error filtering documents: ", exception);
            return null;
        });
    }

}
