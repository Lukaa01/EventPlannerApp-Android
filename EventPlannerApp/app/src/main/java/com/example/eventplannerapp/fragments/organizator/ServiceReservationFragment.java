package com.example.eventplannerapp.fragments.organizator;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.NotificationStatus;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.Employee;
import com.example.eventplannerapp.model.EventOrganizer;
import com.example.eventplannerapp.model.Notification;
import com.example.eventplannerapp.model.ReservationStatus;
import com.example.eventplannerapp.model.Service;
import com.example.eventplannerapp.model.ServiceReservation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class ServiceReservationFragment extends Fragment {

    private Service selectedService;
    private Employee selectedEmployee;
    private TableLayout tableLayout;
    private DatePicker datePicker;
    private List<Employee> employees;
    private List<ServiceReservation> reservations;
    private String dayOfWeekString;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String employeeWorkingHours;

    private String selectedStartTime;
    private String selectedEndTime;

    private List<String> availableTerms = new ArrayList<>();

    private final static String TERM_STARTING = "TERM_STARTING";

    public ServiceReservationFragment() {
        // Required empty public constructor
    }

    public static ServiceReservationFragment newInstance() {
        ServiceReservationFragment fragment = new ServiceReservationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedService = getArguments().getParcelable("selectedService");
            selectedEmployee = getArguments().getParcelable("selectedEmployee");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_service_reservation, container, false);
        tableLayout = root.findViewById(R.id.table_layout);

        datePicker = root.findViewById(R.id.date_picker);

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);

                int dayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK);

                switch (dayOfWeek) {
                    case Calendar.SUNDAY:
                        dayOfWeekString = "Sunday";
                        break;
                    case Calendar.MONDAY:
                        dayOfWeekString = "Monday";
                        break;
                    case Calendar.TUESDAY:
                        dayOfWeekString = "Tuesday";
                        break;
                    case Calendar.WEDNESDAY:
                        dayOfWeekString = "Wednesday";
                        break;
                    case Calendar.THURSDAY:
                        dayOfWeekString = "Thursday";
                        break;
                    case Calendar.FRIDAY:
                        dayOfWeekString = "Friday";
                        break;
                    case Calendar.SATURDAY:
                        dayOfWeekString = "Saturday";
                        break;
                    default:
                        dayOfWeekString = "Unknown";
                }
                loadEmployeeWorkingHours(dayOfWeekString);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String selectedDateString = dateFormat.format(selectedDate.getTime());
                loadExistingReservations(selectedDateString);
            }
        });

        // Initialize lists
        employees = new ArrayList<>();
        reservations = new ArrayList<>();

        // Populate the reservation details
        if (selectedService != null && selectedEmployee != null) {
            TextView serviceName = root.findViewById(R.id.reservation_service_name);
            TextView serviceDescription = root.findViewById(R.id.reservation_service_description);
            TextView servicePrice = root.findViewById(R.id.reservation_service_price);
            TextView employeeName = root.findViewById(R.id.reservation_employee_name);

            serviceName.setText(selectedService.getTitle());
            serviceDescription.setText(selectedService.getDescription());
            servicePrice.setText(String.format("%s din", selectedService.getPrice()));
            employeeName.setText(String.format("%s", selectedEmployee.getFirstname()));
        }

        return root;
    }

    private void loadEmployeeWorkingHours(String dayOfWeekString) {
        if (dayOfWeekString == null || dayOfWeekString.isEmpty()) {
            Log.e("Employee", "loadEmployeeWorkingHours: Invalid day of week string.");
            return;
        }
        String lowercaseDayOfWeek = dayOfWeekString.toLowerCase();

        // Reference to the employee's document in Firestore
        CollectionReference employeesRef = db.collection("employees");

        // Query to get all employees
        employeesRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Iterate through each employee document
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Employee employee = document.toObject(Employee.class);
                    if (employee.getFirstname().equals(selectedEmployee.getFirstname())) {
                        // Found the selected employee
                        Map<String, String> availability = employee.getAvailability().getHoursPerDay();
                        if (availability != null && availability.containsKey(lowercaseDayOfWeek)) {
                            employeeWorkingHours = availability.get(lowercaseDayOfWeek);
                            Log.d("Employee", "Working hours for " + dayOfWeekString + ": " + employeeWorkingHours);
                            generateAvailableTerms();
                        } else {
                            Log.d("Employee", "No working hours available for " + dayOfWeekString);
                        }
                        return; // Exit the loop after finding the selected employee
                    }
                }
                // If the loop completes without finding the selected employee
                Log.d("Employee", "Selected employee not found");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Employee", "Error retrieving employees", e);
            }
        });
    }


    private void loadExistingReservations(String selectedDateString) {
        // Implement logic to load existing reservations for the selected date
        // Populate the 'reservations' list with this information
        CollectionReference serviceReservationsRef = db.collection("serviceReservations");

        // Query for reservations where the employeeName matches selectedEmployee's firstname
        // and the reservationDate matches the selectedDate
        Query query = serviceReservationsRef
                .whereEqualTo("employeeName", selectedEmployee.getFirstname())
                .whereEqualTo("date", selectedDateString); // Assuming selectedDateString is the formatted selected date

        // Execute the query
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Clear existing reservations
                reservations.clear();

                // Iterate through the query results
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    // Convert each document snapshot to a ServiceReservation object
                    ServiceReservation reservation = documentSnapshot.toObject(ServiceReservation.class);
                    // Add the reservation to the reservations list
                    reservations.add(reservation);
                }

                // Now, you can work with the reservations list here
                // For example, you can update the UI or perform further operations
                // based on the fetched reservations.
                generateAvailableTerms();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors that occur during the query execution
                Log.e("reservations", "Error fetching reservations: " + e.getMessage());
            }
        });
    }

    private void generateAvailableTerms() {
        List<String> availableTerms = new ArrayList<>();

        if (employeeWorkingHours == null || employeeWorkingHours.isEmpty()) {
            Log.e("ServiceReservation", "Employee working hours are not set.");
            return;
        }

        if (employeeWorkingHours.equals("closed")) {
            Toast.makeText(getContext(), "Employee doesn't work that day, try another one", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] workingHoursSplit = employeeWorkingHours.split("-");
        if (workingHoursSplit.length < 2) {
            Log.d("ServiceReservation", employeeWorkingHours);
            Log.e("ServiceReservation", "Invalid working hours format.");
            return;
        }

        String workingStartTime = workingHoursSplit[0];
        String workingEndTime = workingHoursSplit[1];

        // Sort reservations by start time
        Collections.sort(reservations, new Comparator<ServiceReservation>() {
            @Override
            public int compare(ServiceReservation r1, ServiceReservation r2) {
                return r1.getStartTime().compareTo(r2.getStartTime());
            }
        });

        String freeStartTime = workingStartTime;

        for (ServiceReservation reservation : reservations) {
            String reservationStartTime = reservation.getStartTime();
            String reservationEndTime = reservation.getEndTime();

            if (isFreeTerm(freeStartTime, reservationStartTime)) {
                availableTerms.add(freeStartTime + "-" + reservationStartTime);
            }
            freeStartTime = reservationEndTime;
        }

        if (isFreeTerm(freeStartTime, workingEndTime)) {
            availableTerms.add(freeStartTime + "-" + workingEndTime);
        }

        if (!availableTerms.isEmpty()) {
            Toast.makeText(getContext(), "Free terms found: " + availableTerms.size(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "No free terms available.", Toast.LENGTH_SHORT).show();
        }

        // Here you can handle the time pickers based on available terms and the min/max duration
        this.availableTerms = availableTerms;
        setupTimePickers(availableTerms);
    }

    private boolean isFreeTerm(String start, String end) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        try {
            Date startTime = timeFormat.parse(start);
            Date endTime = timeFormat.parse(end);

            long durationInMillis = endTime.getTime() - startTime.getTime();
            long minDurationInMillis = getMinDurationInMillis(); // Implement this method to get the minimum duration in milliseconds

            return durationInMillis >= minDurationInMillis;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private long getMinDurationInMillis() {
        // Replace this with your actual minimum duration logic
        /*if ((int) selectedService.getDurationMin() != 0) {
            int minDurationInMinutes = (int) selectedService.getDurationMin(); // Example: 30 minutes
            Log.d("SomethingMili", "Minimum duration: " + minDurationInMinutes + " minutes");
            return (long) minDurationInMinutes * 60 * 1000;
        }*/
        return 30 * 60 * 1000;
    }

    private void setupTimePickers(List<String> availableTerms) {
        tableLayout.removeAllViews(); // Clear any existing views

        for (String term : availableTerms) {
            String[] timeSplit = term.split("-");
            String termStartTime = timeSplit[0];
            String termEndTime = timeSplit[1];

            LinearLayout timePickerLayout = new LinearLayout(getContext());
            timePickerLayout.setOrientation(LinearLayout.VERTICAL);
            timePickerLayout.setPadding(16, 16, 16, 16);

            TextView timeRange = new TextView(getContext());
            timeRange.setText(term);
            timeRange.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            LinearLayout buttonsLayout = new LinearLayout(getContext());
            buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
            buttonsLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            MaterialButton startTimeButton = new MaterialButton(getContext());
            startTimeButton.setText("Pick start time");
            startTimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTimePickerDialog(termStartTime, true, startTimeButton);
                }
            });

            MaterialButton endTimeButton = new MaterialButton(getContext());
            endTimeButton.setText("Pick end time");
            endTimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTimePickerDialog(termEndTime, false, endTimeButton);
                }
            });

            buttonsLayout.addView(startTimeButton);
            buttonsLayout.addView(endTimeButton);

            MaterialButton reserveButton = new MaterialButton(getContext());
            reserveButton.setText("Reserve");
            reserveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle reservation logic here
                    // Use selectedStartTime and selectedEndTime for the reservation times
                    if (selectedStartTime == null || selectedEndTime == null) {
                        Toast.makeText(getContext(), "Please select start and end time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Validate and handle the reservation with the selected start and end times
                    if (validateReservation(selectedStartTime, selectedEndTime)) {
                        makeReservation(selectedStartTime, selectedEndTime);
                    }
                }
            });

            timePickerLayout.addView(timeRange);
            timePickerLayout.addView(buttonsLayout);
            timePickerLayout.addView(reserveButton);

            tableLayout.addView(timePickerLayout);
        }
    }

    private void showTimePickerDialog(String initialTime, boolean isStartTime, Button buttonToUpdate) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = timeFormat.parse(initialTime);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int initialHour = calendar.get(Calendar.HOUR_OF_DAY);
        int initialMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view, selectedHour, selectedMinute) -> {
            String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
            if (isStartTime) {
                selectedStartTime = selectedTime;
            } else {
                selectedEndTime = selectedTime;
            }
            buttonToUpdate.setText(selectedTime);
        }, initialHour, initialMinute, true);

        timePickerDialog.show();
    }

    private boolean validateReservation(String startTime, String endTime) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        try {
            Date start = timeFormat.parse(startTime);
            Date end = timeFormat.parse(endTime);

            long durationInMillis = end.getTime() - start.getTime();
            long minDurationInMillis = getMinDurationInMillis();
            long maxDurationInMillis = (long) selectedService.getDurationMax() * 60 * 60 * 1000; // Max duration in milliseconds

            if (selectedService.getDurationMin() != 0 && (durationInMillis < minDurationInMillis || durationInMillis > maxDurationInMillis)) {
                Toast.makeText(getContext(), "Selected duration must be between " + selectedService.getDurationMin() + " minutes and " + selectedService.getDurationMax() + " hours.", Toast.LENGTH_LONG).show();
                return false;
            }

            // Check if the selected time is within the term range
            for (String term : availableTerms) {
                String[] timeSplit = term.split("-");
                Date termStartTime = timeFormat.parse(timeSplit[0]);
                Date termEndTime = timeFormat.parse(timeSplit[1]);

                if (!start.before(termStartTime) && !end.after(termEndTime) && durationInMillis == maxDurationInMillis) {
                    return true;
                }
            }

            Toast.makeText(getContext(), "Selected time is outside of the available terms.", Toast.LENGTH_LONG).show();
            return false;

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void makeReservation(String startTime, String endTime) {
        // Get the selected date from the datePicker
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String selectedDateString = dateFormat.format(calendar.getTime());

        // Create a new ServiceReservation object
        ServiceReservation reservation = new ServiceReservation();
        reservation.setId(UUID.randomUUID().toString());
        reservation.setService(selectedService);
        reservation.setReservationStatus(ReservationStatus.NEW);
        reservation.setEmployeeName(selectedEmployee.getFirstname());
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setDate(selectedDateString);

        // Fetch organizer's email from Firestore
        CollectionReference eventOrganizersRef = db.collection("event_organizers");

        eventOrganizersRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        EventOrganizer organizer = documentSnapshot.toObject(EventOrganizer.class);
                        Log.d("organizer", organizer.getEmail());
                        reservation.setEmail(organizer.getEmail());
                        // Once we have the organizer's email, add the reservation to the database
                        addReservationToFirestore(reservation);
                        break;
                    }
                } else {
                    Toast.makeText(getContext(), "No organizers found", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Failed to fetch organizers", Toast.LENGTH_SHORT).show();
                Log.e("FirestoreError", "Error fetching organizers", e);
            }
        });
    }

    private void addReservationToFirestore(ServiceReservation reservation) {
        // Add the reservation to the Firestore database
        CollectionReference serviceReservationsRef = db.collection("serviceReservations");

        serviceReservationsRef.add(reservation)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "Reservation made successfully!", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Notification notification = new Notification();
                                notification.setId(UUID.randomUUID().toString());
                                notification.setDescription("Your term is about to start");
                                notification.setEmail("milibovan190d@gmail.com");
                                notification.setNotificationType(TERM_STARTING);
                                notification.setNotificationStatus(NotificationStatus.UNREAD);
                                CloudStoreUtil.insertNotification(notification);
                            }
                        }, 10000); // 10 seconds delay
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to make reservation: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }


}
