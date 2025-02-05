package com.example.eventplannerapp.fragments.vlasnik;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.eventplannerapp.R;

import java.time.LocalTime;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEmployeeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText editTextWorkingHoursStart;
    private EditText editTextWorkingHoursEnd;


    public CreateEmployeeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEmployeeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEmployeeFragment newInstance(String param1, String param2) {
        CreateEmployeeFragment fragment = new CreateEmployeeFragment();
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

        View view = inflater.inflate(R.layout.fragment_create_employee, container, false);

        editTextWorkingHoursStart = view.findViewById(R.id.editTextWorkingHoursStart);
        editTextWorkingHoursEnd = view.findViewById(R.id.editTextWorkingHoursEnd);

        editTextWorkingHoursStart.setOnClickListener(v -> showTimePickerDialog(true));
        editTextWorkingHoursEnd.setOnClickListener(v -> showTimePickerDialog(false));

        return view;
    }

    private void showTimePickerDialog(boolean isStartTime) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), (view, hourOfDay, minuteOfHour) -> {
            LocalTime time = LocalTime.of(hourOfDay, minuteOfHour);
            String formattedTime = time.toString();
            if (isStartTime) {
                editTextWorkingHoursStart.setText(formattedTime);
            } else {
                editTextWorkingHoursEnd.setText(formattedTime);
            }
        }, hour, minute, true);

        timePickerDialog.show();

    }
}