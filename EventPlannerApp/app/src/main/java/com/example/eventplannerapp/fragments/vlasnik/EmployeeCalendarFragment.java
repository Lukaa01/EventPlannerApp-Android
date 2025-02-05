package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.FragmentEmployeeCalendarBinding;
import com.example.eventplannerapp.databinding.FragmentManageEmployeesBinding;
import com.example.eventplannerapp.model.Employee;
import com.example.eventplannerapp.model.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class EmployeeCalendarFragment extends Fragment {

    public static ArrayList<Event> events = new ArrayList<>();
    private EmployeeEventViewModel eventViewModel;
    private FragmentEmployeeCalendarBinding binding;
    private NavController navController;

    public EmployeeCalendarFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        eventViewModel = new ViewModelProvider(this).get(EmployeeEventViewModel.class);

        binding = FragmentEmployeeCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        events = new ArrayList<>();
        prepareEventsList(events);

        SearchView searchView = binding.searchTextEvents;

        eventViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);

        FloatingActionButton floatingAddButton = binding.floatingAddEventButton;
        floatingAddButton.setOnClickListener(v -> {
            navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
            navController.navigate(R.id.nav_add_employee_event);
        });


        FragmentTransition.to(EmployeeEventListFragment.newInstance(events), getActivity(), false, R.id.scroll_events_list);

        return root;
    }

    private void prepareEventsList(ArrayList<Event> events) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        events.add(new Event(1l, "Sastanak sa osobom", LocalDate.parse("12.12.2022", dateFormatter), LocalTime.parse("12:11", timeFormatter), LocalTime.parse("13:45", timeFormatter), true));
        events.add(new Event(2l, "Sastanak sa personom", LocalDate.parse("13.12.2022", dateFormatter), LocalTime.parse("12:42", timeFormatter), LocalTime.parse("13:33", timeFormatter), false));
        events.add(new Event(3l, "Sastanak sa malicioznim", LocalDate.parse("21.12.2022", dateFormatter), LocalTime.parse("11:54", timeFormatter), LocalTime.parse("16:45", timeFormatter), false));
        events.add(new Event(4l, "Sastanak sa direktorom", LocalDate.parse("22.12.2022", dateFormatter), LocalTime.parse("18:51", timeFormatter), LocalTime.parse("20:45", timeFormatter), true));
    }
}