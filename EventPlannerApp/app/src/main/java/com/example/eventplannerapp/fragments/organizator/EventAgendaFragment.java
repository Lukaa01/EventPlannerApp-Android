package com.example.eventplannerapp.fragments.organizator;

import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.FragmentEventAgendaBinding;
import com.example.eventplannerapp.model.Event1;
import com.example.eventplannerapp.model.EventActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventAgendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventAgendaFragment extends Fragment {


    public static ArrayList<EventActivity> activities = new ArrayList<>();
    private EditText editTextName;
    private EditText editTextDesc;
    private EditText editTextLocation;
    private TimePicker  editTextStart;
    private TimePicker  editTextEnd;

    private NavController navController;

    private FragmentEventAgendaBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventAgendaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventAgendaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventAgendaFragment newInstance(String param1, String param2) {
        EventAgendaFragment fragment = new EventAgendaFragment();
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
        View view = inflater.inflate(R.layout.fragment_event_agenda, container, false);
        editTextName = view.findViewById(R.id.etActivityName);
        editTextDesc = view.findViewById(R.id.etActivityDescription);
        editTextLocation = view.findViewById(R.id.etLocation);
        editTextStart = view.findViewById(R.id.tpStartTime);
        editTextEnd = view.findViewById(R.id.tpEndTime);
        binding = FragmentEventAgendaBinding.inflate(inflater, container, false);
        Button generatePdf = view.findViewById(R.id.btnSeeAgenda);
        activities = new ArrayList<>();
        activities = prepareActivityList(activities);
        generatePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EventActivity", "Activity size: " + activities.size());
                generatePdf(activities);

            }
        });

        Button addActivityButton = view.findViewById(R.id.btnAddActivity);
        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String activityName = editTextName.getText().toString();
                String activityDescription = editTextDesc.getText().toString();
                String location = editTextLocation.getText().toString();
                TimePicker tpStartTime = editTextStart;
                TimePicker tpEndTime = editTextEnd;
                String id = generateRandomId();

                int startHour = tpStartTime.getCurrentHour();
                int startMinute = tpStartTime.getCurrentMinute();
                int endHour = tpEndTime.getCurrentHour();
                int endMinute = tpEndTime.getCurrentMinute();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                String startTime = LocalTime.of(startHour, startMinute).format(formatter);
                String endTime = LocalTime.of(endHour, endMinute).format(formatter);
                EventActivity eventActivity = new EventActivity(id, activityName, activityDescription, startTime, endTime, location);

                CloudStoreUtil.insertEventActivity(eventActivity);
                editTextName.setText("");
                editTextDesc.setText("");
                editTextLocation.setText("");
            }
        });


        Button createGuestListButton = view.findViewById(R.id.btnCreateGuestList);
        createGuestListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main_org);
                navController.navigate(R.id.nav_createGuestList);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }


    public static String generateRandomId() {
        // Generate a random UUID
        UUID uuid = UUID.randomUUID();
        // Convert UUID to String and remove hyphens
        String randomId = uuid.toString().replace("-", "");
        // Return the random ID
        return randomId;
    }


    private void generatePdf(ArrayList<EventActivity> activities) {

        Log.d("EventActivity", "Activity size in generate pdf: " + activities.size());

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(612, 792, 1).create(); // Standard US Letter size: 8.5 x 11 inches

        // Start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Paint object for the title
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(32); // Larger text size for the title
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Draw the title
        canvas.drawText("Event Agenda", 50, 50, titlePaint);

        // Paint object for the event details
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(18); // Smaller text size for the event details

        int y = 100; // Y coordinate for the start of the event details, after the title

        // Print details of each event on the PDF page
        for (int i = 0; i < activities.size(); i++) {
            EventActivity activity = activities.get(i);
            Log.d("EventActivity", "Activity Name: " + activity.getActivityName());
            canvas.drawText("Activity Name: " + activity.getActivityName(), 50, y, paint);
            y += 25;
            canvas.drawText("Description: " + activity.getActivityDescription(), 50, y, paint);
            y += 25;
            canvas.drawText("Location: " + activity.getLocation(), 50, y, paint);
            y += 25;
            canvas.drawText("Start Time: " + activity.getStartTime(), 50, y, paint);
            y += 25;
            canvas.drawText("End Time: " + activity.getEndTime(), 50, y, paint);
            y += 40; // Space between each event
        }

        // Finish the page
        document.finishPage(page);

        // Define the file path to save the PDF
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "mobOdbranaEventAgenda.pdf");

        try {
            // Save the PDF to the specified location
            document.writeTo(new FileOutputStream(file));
            // Notify the user that the PDF was generated successfully
            Toast.makeText(getContext(), "PDF generated successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // Handle IOException if any occurs
            e.printStackTrace();
            // Notify the user about the failure to generate the PDF
            Toast.makeText(getContext(), "Failed to generate PDF!", Toast.LENGTH_SHORT).show();
        } finally {
            // Close the document to release resources
            document.close();
        }
    }


    private ArrayList<EventActivity> prepareActivityList(ArrayList<EventActivity> activities) {
        CloudStoreUtil.selectAllEventActivities().thenAccept(list -> {
            activities.addAll(list);

        }).exceptionally(exception -> {
            return null;
        });
        return activities;
    }


}