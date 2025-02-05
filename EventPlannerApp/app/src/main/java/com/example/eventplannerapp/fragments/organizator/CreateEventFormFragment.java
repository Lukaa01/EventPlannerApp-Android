package com.example.eventplannerapp.fragments.organizator;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.EventType;
import com.example.eventplannerapp.NotificationStatus;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.EventTypeAdapter;
import com.example.eventplannerapp.databinding.FragmentCreateEventFormBinding;
import com.example.eventplannerapp.model.Event1;
import com.example.eventplannerapp.model.Notification;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEventFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventFormFragment extends Fragment {

    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextLocation;
    private EditText editTextMaxKm;
    private EditText editTextNumGuests;

    private NavController navController;
    private FragmentCreateEventFormBinding binding;
    private EditText dateEditText;
    private Calendar calendar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateEventFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventFormFragment newInstance(String param1, String param2) {
        CreateEventFormFragment fragment = new CreateEventFormFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_event_form, container, false);
        editTextName = view.findViewById(R.id.editTextName);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextLocation = view.findViewById(R.id.editTextLocation);
        editTextMaxKm = view.findViewById(R.id.editTextMaxKm);
        editTextNumGuests = view.findViewById(R.id.editTextNumGuests);
        dateEditText = view.findViewById(R.id.dateEditText);

        binding = FragmentCreateEventFormBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dateEditText = view.findViewById(R.id.dateEditText);


        Spinner spinnerEventType = view.findViewById(R.id.spinnerEventType);
        EventType[] eventTypes = EventType.values();
        EventTypeAdapter adapter = new EventTypeAdapter(requireContext(), eventTypes);
        spinnerEventType.setAdapter(adapter);

        spinnerEventType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EventType selectedEventType = (EventType) parent.getItemAtPosition(position);
                // You can use selectedEventType here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        Button createEventButton = view.findViewById(R.id.button_Create_Event);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {/*
                // Dohvatanje unetih podataka
                String name = editTextName.getText().toString();
                String description = editTextDescription.getText().toString();
                String location = editTextLocation.getText().toString();
                String maxKm = editTextMaxKm.getText().toString();
                String date = dateEditText.getText().toString();
                String numGuestsStr = editTextNumGuests.getText().toString().trim();
                String id = generateRandomId();
                if (name.isEmpty() || description.isEmpty() || location.isEmpty() || maxKm.isEmpty() || date.isEmpty() || numGuestsStr.isEmpty()) {
                    // Show error dialog indicating that all fields are required
                    showDialog("All fields are required");
                }
                else {
                    int numGuests;
                    try {
                        numGuests = Integer.parseInt(numGuestsStr);
                    } catch (NumberFormatException e) {
                        // Show error dialog indicating invalid input for numGuests
                        showDialog("Invalid number of guests");
                        return; // Stop further execution
                    }

                    EventType selectedEventType = (EventType) spinnerEventType.getSelectedItem();

                    Event1 event = new Event1(id,name, description, location, selectedEventType.toString(), date, numGuests);
                    CloudStoreUtil.insertEvent(event);

                    editTextName.setText("");
                    editTextDescription.setText("");
                    editTextLocation.setText("");
                    editTextMaxKm.setText("");
                    dateEditText.setText("");
                    editTextNumGuests.setText("");

                    navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main_org);
                    navController.navigate(R.id.nav_budget_plan);
                }*/
/*
                Notification notification = new Notification();
                notification.setEmail("admin@gmail.com");
                notification.setDescription("");
                notification.setNotificationType("NEW_EVENT");
                notification.setNotificationStatus(NotificationStatus.UNREAD);

                CloudStoreUtil.insertNotification(notification);*/

                navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main_org);
                navController.navigate(R.id.nav_event_agenda);

            }
        });

        /**
         Button addButton = binding.buttonAddEmployee; // Assuming buttonAddEmployee is the id of your regular Button
         addButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
        navController.navigate(R.id.nav_create_employee);
        }
        });
         */




        calendar = Calendar.getInstance();
        // Inflate the layout for this fragment
        return view;
    }
    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };

    private void updateDate() {
        String myFormat = "MM/dd/yy"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateEditText.setText(sdf.format(calendar.getTime()));
    }
    public static String generateRandomId() {
        // Generate a random UUID
        UUID uuid = UUID.randomUUID();
        // Convert UUID to String and remove hyphens
        String randomId = uuid.toString().replace("-", "");
        // Return the random ID
        return randomId;
    }


}
