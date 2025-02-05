package com.example.eventplannerapp.fragments.admin;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.RatingListAdapter;
import com.example.eventplannerapp.activities.adapters.RatingReportListAdapter;
import com.example.eventplannerapp.databinding.FragmentManageRatingReportsBinding;
import com.example.eventplannerapp.databinding.FragmentManageRatingsBinding;
import com.example.eventplannerapp.fragments.vlasnik.OnRatingReportSelectedListener;
import com.example.eventplannerapp.fragments.vlasnik.RatingListFragment;
import com.example.eventplannerapp.fragments.vlasnik.RatingPageViewModel;
import com.example.eventplannerapp.model.Notification;
import com.example.eventplannerapp.model.Rating;
import com.example.eventplannerapp.model.RatingReport;
import com.example.eventplannerapp.model.RatingReportStatus;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Date;

public class ManageRatingReportsFragment extends Fragment implements OnRatingReportSelectedListener {

    private static final String TAG = "YourClass";
    public static ArrayList<RatingReport> reports = new ArrayList<RatingReport>();

    private RatingReportViewModel ratingsViewModel;
    private FragmentManageRatingReportsBinding binding;
    private RatingReportListAdapter reportListAdapter;
    private NavController navController;
    private RatingReport selectedReport;
    private ArrayList<RatingReport> allReports;

    public ManageRatingReportsFragment() { }

    public static ManageRatingReportsFragment newInstance() { return new ManageRatingReportsFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        allReports = new ArrayList<>();
        reports = new ArrayList<>();

        binding = FragmentManageRatingReportsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareReportList(reports);

        reportListAdapter = new RatingReportListAdapter(getContext(), allReports);
        reportListAdapter.setOnRatingSelectedListener(report -> {
            selectedReport = report;
        });

        // TODO: Add buttons on-click listener
        FloatingActionButton rejectButton = root.findViewById(R.id.floating_reject_report);
        FloatingActionButton acceptButton = root.findViewById(R.id.floating_accept_report);
        rejectButton.setOnClickListener(v -> rejectRating());
        acceptButton.setOnClickListener(v -> acceptRating());

        return root;
    }

    private void acceptRating() {
        if (selectedReport == null) {
            Toast.makeText(getContext(), "Please select a rating report first.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedReport.getStatus() != RatingReportStatus.SENT) {
            Toast.makeText(getContext(), "This report is already handled.", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Accept Rating Report")
                .setMessage("Are you sure you want to accept this rating report?")
                .setPositiveButton("Yes", (dialog, which) -> {

                    int index = allReports.indexOf(selectedReport);
                    allReports.get(index).setStatus(RatingReportStatus.ACCEPTED);

                    reportListAdapter.notifyDataSetChanged();
                    refreshListView();

                    CloudStoreUtil.updateRatingReport(selectedReport);

                    Toast.makeText(getContext(), "Rating report accepted.", Toast.LENGTH_SHORT).show();

                    CloudStoreUtil.deleteRating(selectedReport.getRating_id());

                    Notification notification = new Notification(
                            "Admin accepted your report! Comment that you reported will be deleted.",
                            "owner@gmail.com",
                            "RATING_REPORT_ACCEPTED");
                    CloudStoreUtil.insertNotification(notification);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void rejectRating() {
        if (selectedReport == null) {
            Toast.makeText(getContext(), "Please select a rating report first.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedReport.getStatus() != RatingReportStatus.SENT) {
            Toast.makeText(getContext(), "This report is already handled.", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_reject_rating_report, null);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        EditText rejectReason = dialogView.findViewById(R.id.reject_reason);
        Button sendButton = dialogView.findViewById(R.id.send_button);

        sendButton.setOnClickListener(v -> {
            String reason = rejectReason.getText().toString().trim();
            if (reason.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a reason.", Toast.LENGTH_SHORT).show();
            } else {
                int index = allReports.indexOf(selectedReport);
                allReports.get(index).setRejectReason(reason);
                allReports.get(index).setStatus(RatingReportStatus.REJECTED);

                reportListAdapter.notifyDataSetChanged();
                refreshListView();

                CloudStoreUtil.updateRatingReport(selectedReport);

                Toast.makeText(getContext(), "Rating report sent.", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();

                Notification notification = new Notification(
                        "Admin rejected your report! Comment that you reported will not be deleted.",
                        "owner@gmail.com",
                        "RATING_REPORT_REJECTED");
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

    private void refreshListView() {
        RatingReportListFragment reportListFragment = RatingReportListFragment.newInstance(allReports, this);
        FragmentTransition.to(reportListFragment, getActivity(), false, R.id.scroll_report_list);
    }

    private void prepareReportList(ArrayList<RatingReport> reports) {
        CloudStoreUtil.selectAllRatingReports().thenAccept(reportList -> {
            reports.addAll(reportList);
            RatingReportListFragment reportListFragment = RatingReportListFragment.newInstance(reports, this);
            FragmentTransition.to(reportListFragment, getActivity(), false, R.id.scroll_report_list);
            this.allReports.addAll(reports);
        }).exceptionally(exception -> {
            return null;
        });

        Log.i("REZ_DB", "SELECT DATA");
    }

    @Override
    public void onRatingReportSelected(RatingReport report) {
        selectedReport = report;
    }


}