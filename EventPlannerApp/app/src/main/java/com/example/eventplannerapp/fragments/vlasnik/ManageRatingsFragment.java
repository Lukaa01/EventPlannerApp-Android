package com.example.eventplannerapp.fragments.vlasnik;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.NotificationStatus;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.VlasnikHomeActivity;
import com.example.eventplannerapp.activities.adapters.RatingListAdapter;
import com.example.eventplannerapp.databinding.FragmentManageRatingsBinding;
import com.example.eventplannerapp.model.Notification;
import com.example.eventplannerapp.model.Rating;
import com.example.eventplannerapp.model.RatingReport;
import com.example.eventplannerapp.model.RatingReportStatus;
import com.example.eventplannerapp.services.SyncService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ManageRatingsFragment extends Fragment implements OnRatingSelectedListener {

    private static final String TAG = "ManageRatingsFragment";
    public static ArrayList<Rating> ratings = new ArrayList<Rating>();
    private FragmentManageRatingsBinding binding;
    private RatingListAdapter ratingListAdapter;
    private NavController navController;
    private Rating selectedRating;
    private ArrayList<Rating> allRatings;

    public static ManageRatingsFragment newInstance() { return new ManageRatingsFragment(); }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        allRatings = new ArrayList<>();

        String activityName = getActivity().getClass().getSimpleName();
        Log.d(TAG, activityName);

        binding = FragmentManageRatingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ratings = new ArrayList<>();
        prepareRatingList(ratings);

        RatingListAdapter adapter = new RatingListAdapter(getContext(), allRatings);
        adapter.setOnRatingSelectedListener(rating -> {
            selectedRating = rating;
            Log.i("ShopApp", "Selected rating ID: " + selectedRating.getId());
        });

        Button filterButton = binding.getRoot().findViewById(R.id.filter_button);
        filterButton.setOnClickListener(v -> showDatePickerDialog());

        FloatingActionButton reportButton = binding.getRoot().findViewById(R.id.floating_report_rating);
        reportButton.setOnClickListener(v -> showReportDialog());

        if (activityName.equals("VlasnikHomeActivity")) {
            // The activity name is "MainActivity"
            reportButton.setVisibility(View.VISIBLE);
        } else {
            // The activity name is not "MainActivity"
            reportButton.setVisibility(View.INVISIBLE);
        }

        return root;
    }

    private void showReportDialog() {
        if (selectedRating == null) {
            Toast.makeText(getContext(), "Please select a rating first.", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_report_rating, null);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        EditText reportReason = dialogView.findViewById(R.id.report_reason);
        Button sendButton = dialogView.findViewById(R.id.send_button);

        sendButton.setOnClickListener(v -> {
            String reason = reportReason.getText().toString().trim();
            if (reason.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a reason.", Toast.LENGTH_SHORT).show();
            } else {
                RatingReport report = new RatingReport("",
                        selectedRating.getId(),
                        "PUP-V koji je kliknuo (nemamo auth)",
                        new Date(),
                        reason,
                        RatingReportStatus.SENT,
                        null);

                CloudStoreUtil.insertRatingReport(report);

                Toast.makeText(getContext(), "Rating report sent.", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();

                Notification notification = new Notification(
                        "PUP-V sent rating report! Check all rating reports tab.",
                        "admin@gmail.com",
                        "RATING_REPORT_SENT");
                CloudStoreUtil.insertNotification(notification);
            }
        });

        alertDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareRatingList(ArrayList<Rating> ratings) {
        CloudStoreUtil.selectAllRatings().thenAccept(ratingList -> {
            ratings.addAll(ratingList);
            RatingListFragment ratingListFragment = RatingListFragment.newInstance(ratings, this);
            FragmentTransition.to(ratingListFragment, getActivity(), false, R.id.scroll_ratings_list);
            this.allRatings.addAll(ratings);
        }).exceptionally(exception -> {
            return null;
        });

        Log.i("REZ_DB", "SELECT DATA");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            filterRatingsByDate(calendar.getTime());
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void filterRatingsByDate(Date date) {
        if (allRatings == null) {
            Log.e(TAG, "allRatings is null");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String selectedDate = sdf.format(date);

        ArrayList<Rating> filteredRatings = new ArrayList<>();
        for (Rating rating : allRatings) {
            String ratingDate = sdf.format(rating.getDate()); // Assuming `Rating` has a `getDate` method returning `Date`
            if (selectedDate.equals(ratingDate)) {
                filteredRatings.add(rating);
            }
        }

        updateRatingListFragment(filteredRatings);
    }

    private void updateRatingListFragment(ArrayList<Rating> ratings) {
        RatingListFragment ratingListFragment = RatingListFragment.newInstance(ratings, this);
        FragmentTransition.to(ratingListFragment, getActivity(), false, R.id.scroll_ratings_list);
    }

    @Override
    public void onRatingSelected(Rating rating) {
        selectedRating = rating;
        Log.i("ManageRatingsFragment", "Selected rating: " + selectedRating.getComment());
    }
}