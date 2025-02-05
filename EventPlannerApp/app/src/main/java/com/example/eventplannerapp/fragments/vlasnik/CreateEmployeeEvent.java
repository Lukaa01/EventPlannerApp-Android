package com.example.eventplannerapp.fragments.vlasnik;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.eventplannerapp.R;

import java.util.Locale;

public class CreateEmployeeEvent extends Fragment {

    private EditText eventDate, eventStartTime, eventEndTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_employee_event, container, false);

        eventDate = view.findViewById(R.id.eventDate);
        eventStartTime = view.findViewById(R.id.eventStartTime);
        eventEndTime = view.findViewById(R.id.eventEndTime);

        eventDate.setOnClickListener(v -> showDatePickerDialog());
        eventStartTime.setOnClickListener(v -> showTimePickerDialog(eventStartTime));
        eventEndTime.setOnClickListener(v -> showTimePickerDialog(eventEndTime));

        return view;
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(getContext(), (view, year1, monthOfYear, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
            eventDate.setText(selectedDate);
        }, year, month, day).show();
    }

    private void showTimePickerDialog(final EditText timeField) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        new TimePickerDialog(getContext(), (view, hourOfDay, minuteOfHour) -> {
            String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minuteOfHour);
            timeField.setText(selectedTime);
        }, hour, minute, true).show();
    }
}