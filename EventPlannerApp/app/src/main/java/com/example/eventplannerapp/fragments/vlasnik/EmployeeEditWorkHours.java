package com.example.eventplannerapp.fragments.vlasnik;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.eventplannerapp.R;

import java.sql.Time;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeEditWorkHours#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeEditWorkHours extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmployeeEditWorkHours() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployeeEditWorkHours.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeeEditWorkHours newInstance(String param1, String param2) {
        EmployeeEditWorkHours fragment = new EmployeeEditWorkHours();
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

        View view = inflater.inflate(R.layout.fragment_employee_edit_work_hours, container, false);

        setupTimePickers(view);

        EditText startDateField = view.findViewById(R.id.start_date);
        EditText endDateField = view.findViewById(R.id.end_date);

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = (picker_view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault());
            startDateField.setText(format.format(calendar.getTime()));
        };

        startDateField.setOnClickListener(v -> {
            new DatePickerDialog(getActivity(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        endDateField.setOnClickListener(v -> {
            new DatePickerDialog(getActivity(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        return view;
    }

    private void setupTimePickers(View view) {
        int[] timePickerIds = new int[]{R.id.monday_start, R.id.monday_end, R.id.tuesday_start, R.id.tuesday_end, R.id.wednesday_start, R.id.wednesday_end, R.id.thursday_start, R.id.thursday_end, R.id.friday_start, R.id.friday_end};
        for (int id : timePickerIds) {
            EditText timeField = view.findViewById(id);
            timeField.setOnClickListener(v -> showTimePickerDialog(timeField));
        }
    }

    private void showTimePickerDialog(final EditText timeField) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view, hourOfDay, minuteOfHour) -> {
            // Formatting time in HH:mm format
            String formattedTime = String.format("%02d:%02d", hourOfDay, minuteOfHour);
            timeField.setText(formattedTime);
        }, hour, minute, DateFormat.is24HourFormat(getContext()));

        timePickerDialog.show();
    }
}