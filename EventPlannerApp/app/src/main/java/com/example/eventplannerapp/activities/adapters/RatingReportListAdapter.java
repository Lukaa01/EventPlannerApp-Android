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

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.RatingReport;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;

import javax.annotation.Nullable;

public class RatingReportListAdapter extends ArrayAdapter<RatingReport> {

    private ArrayList<RatingReport> aReports;
    private Consumer<RatingReport> mListener;

    public RatingReportListAdapter(Context context, ArrayList<RatingReport> reports) {
        super(context, R.layout.rating_report_card, reports);
        aReports = reports;
    }

    @Override
    public int getCount() { return aReports.size(); }

    @Nullable
    @Override
    public RatingReport getItem(int position) { return aReports.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @NotNull
    @Override
    public View getView(int position, @Nullable View convertView, @NotNull ViewGroup parent) {
        RatingReport report = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rating_report_card, parent, false);
        }
        LinearLayout reportCard = convertView.findViewById(R.id.rating_report_card_item);
        TextView reportId = convertView.findViewById(R.id.report_rating_id);
        TextView reportUsername = convertView.findViewById(R.id.report_username);
        TextView reportDate = convertView.findViewById(R.id.report_date);
        TextView reportReason = convertView.findViewById(R.id.report_reason);
        TextView reportStatus = convertView.findViewById(R.id.report_status);
        TextView reportRejectReason = convertView.findViewById(R.id.report_reject_reason);

        if (report == null) {
            return convertView;
        }

        reportId.setText(report.getId());
        reportUsername.setText(report.getUsername());
        reportDate.setText(report.getDate().toString());
        reportReason.setText(report.getReason());
        reportStatus.setText(report.getStatus().name());
        reportRejectReason.setText(report.getRejectReason());

        reportCard.setOnClickListener(v -> {
            Log.i("ShopApp", "Clicked: Report ID: " + report.getId());
            Toast.makeText(getContext(), "Clicked report ID: " + reportId.getId(), Toast.LENGTH_SHORT).show();
            if (mListener != null) {
                mListener.accept(report);
            }
        });

        return convertView;
    }

    public void setOnRatingSelectedListener(Consumer<RatingReport> listener) { mListener = listener; }
}
