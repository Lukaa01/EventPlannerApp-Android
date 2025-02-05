package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.FragmentManageEmployeesBinding;
import com.example.eventplannerapp.model.Employee;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ManageEmployeesFragment extends Fragment {

    public static ArrayList<Employee> employees = new ArrayList<>();
    private EmployeePageViewModel employeeViewModel;
    private FragmentManageEmployeesBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        employeeViewModel = new ViewModelProvider(this).get(EmployeePageViewModel.class);

        binding = FragmentManageEmployeesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        employees = new ArrayList<>();
        prepareEmployeeList(employees);

        SearchView searchView = binding.searchTextEmployees;

        employeeViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);


        FloatingActionButton floatingAddButton = binding.floatingAddEmployeeButton;
        floatingAddButton.setOnClickListener(v -> {
            navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
            navController.navigate(R.id.nav_create_employee);
        });

        FloatingActionButton floatingEditWorkHoursButton = binding.floatingEditWorkHoursEmployeeActivityButton;
        floatingEditWorkHoursButton.setOnClickListener(v -> {
            navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
            navController.navigate(R.id.nav_employee_edit_work_hours);
        });

        FloatingActionButton floatingCalendarButton = binding.floatingViewCalendarButton;
        floatingCalendarButton.setOnClickListener(v -> {
            navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
            navController.navigate(R.id.nav_employee_calendar);
        });

        FragmentTransition.to(EmployeeListFragment.newInstance(employees), getActivity(), false, R.id.scroll_employees_list);

        return root;
    }

    private void prepareEmployeeList(ArrayList<Employee> employees) {
        /*employees.add(new Employee(1L, "jovan@jovan.com", "jovan", "Jovan", "Dezic", "Jovanova ulica 3", "06123456789", true, LocalTime.of(9, 0), LocalTime.of(16, 0)));
        employees.add(new Employee(2L, "meli@meli.com", "meli", "Meli", "Dezic", "Jovanova ulica 344", "06123456789", true, LocalTime.of(9, 0), LocalTime.of(16, 0)));
        employees.add(new Employee(3L, "melanko@melanko.com", "melanko", "Melanko", "Dezic", "Jovanova ulica 3222", "06123456789", true, LocalTime.of(9, 0), LocalTime.of(16, 0)));
        employees.add(new Employee(4L, "zela@zela.com", "zela", "Zela", "Dezic", "Jovanova ulica 4213", "06123456789", true, LocalTime.of(9, 0), LocalTime.of(16, 0)));
        employees.add(new Employee(5L, "alex@company.com", "alexSecure", "Alex", "Johnson", "Maple Street 12", "0623456789", true, LocalTime.of(8, 30), LocalTime.of(17, 0)));
        employees.add(new Employee(6L, "maya@company.com", "mayaSecure", "Maya", "Smith", "Oak Avenue 45", "0634567890", true, LocalTime.of(10, 0), LocalTime.of(18, 30)));
        employees.add(new Employee(7L, "jake@company.com", "jakeSecure", "Jake", "Williams", "Pine Road 78", "0645678901", true, LocalTime.of(9, 15), LocalTime.of(17, 15)));
        employees.add(new Employee(8L, "lily@company.com", "lilySecure", "Lily", "Brown", "Elm Street 89", "0656789012", true, LocalTime.of(8, 0), LocalTime.of(16, 0)));*/
    }
}