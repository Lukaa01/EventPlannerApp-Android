package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.EmployeeListAdapter;
import com.example.eventplannerapp.adapters.EmployeeAdapter;
import com.example.eventplannerapp.databinding.FragmentEmployeeListBinding;
import com.example.eventplannerapp.databinding.FragmentManageEmployeesBinding;
import com.example.eventplannerapp.model.Employee;

import java.util.ArrayList;

public class EmployeeListFragment extends ListFragment {

    private EmployeeListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<Employee> mEmployees;
    private FragmentManageEmployeesBinding binding;


    public EmployeeListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EmployeeListFragment newInstance(ArrayList<Employee> employees) {
        EmployeeListFragment fragment = new EmployeeListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, employees);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEmployees = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new EmployeeListAdapter(getActivity(), mEmployees);
            setListAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_list, container, false);
    }
}