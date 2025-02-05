package com.example.eventplannerapp.activities.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.Employee;

import java.util.ArrayList;

public class EmployeeListAdapter extends ArrayAdapter<Employee> {

    private ArrayList<Employee> aEmployees;

    public EmployeeListAdapter(Context context, ArrayList<Employee> employees) {
        super(context, R.layout.employee_card, employees);
        aEmployees = employees;
    }

    @Override
    public int getCount() {
        return aEmployees.size();
    }

    @Nullable
    @Override
    public Employee getItem(int position) {
        return aEmployees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Employee employee = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.employee_card,
                    parent, false);
        }
        LinearLayout employeeCard = convertView.findViewById(R.id.employee_card_item);
        TextView employeeName = convertView.findViewById(R.id.employee_name);
        TextView employeeEmail = convertView.findViewById(R.id.employee_email);
        TextView employeePhoneNumber = convertView.findViewById(R.id.employee_phone_number);
        TextView employeeAddress = convertView.findViewById(R.id.employee_address);
        TextView employeeWorkingHours = convertView.findViewById(R.id.employee_working_hours);
        TextView employeeActive = convertView.findViewById(R.id.employee_active);

        if(employee != null){
            employeeName.setText(String.format("%s %s", employee.getFirstname(), employee.getLastName()));
            employeeEmail.setText(employee.getEmail());
            employeePhoneNumber.setText(employee.getPhoneNumber());
            employeeAddress.setText(employee.getAddress());
            //employeeWorkingHours.setText(String.format("%s - %s", employee.getWorkingHoursStartTime().toString(), employee.getWorkingHoursEndTime().toString()));
            employeeActive.setText(employee.isAccountActive() ? "Active" : "Inactive");

            employeeCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + employee.getLastName() + ", id: " +
                        employee.getId().toString());
                Toast.makeText(getContext(), "Clicked: " + employee.getLastName()  +
                        ", id: " + employee.getId().toString(), Toast.LENGTH_SHORT).show();
            });
        }

        return convertView;
    }
}
