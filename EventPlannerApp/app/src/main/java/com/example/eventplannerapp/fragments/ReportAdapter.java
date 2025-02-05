package com.example.eventplannerapp.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.NotificationStatus;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.ReportStatus;
import com.example.eventplannerapp.databinding.FragmentItemListBinding;
import com.example.eventplannerapp.databinding.FragmentPriceListBinding;
import com.example.eventplannerapp.fragments.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.eventplannerapp.databinding.FragmentItemBinding;
import com.example.eventplannerapp.model.EventOrganizer;
import com.example.eventplannerapp.model.Notification;
import com.example.eventplannerapp.model.Owner;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Report;
import com.example.eventplannerapp.model.Service;
import com.example.eventplannerapp.model.Package;
import com.example.eventplannerapp.model.Category;
import com.example.eventplannerapp.services.SyncService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ReportAdapter extends ArrayAdapter<Report> {
    private final static String REPORT_DECLINED = "REPORT_DECLINED";
    private final static String USER_BLOCKED = "USER_BLOCKED";
    private final static String RESERVATION_CANCELED = "RESERVATION_CANCELED";
    private final static String RESERVATION_CANCELED_OWNER = "RESERVATION_CANCELED_OWNER";
    private final ArrayList<Report> aReports;
    private Context context;
    private FragmentItemListBinding binding;
    private final String[] permissions = {Manifest.permission.POST_NOTIFICATIONS};

    public ReportAdapter(Context c, ArrayList<Report> reports) {
        super(c, R.layout.fragment_item, reports);
        context = c;
        aReports = reports;
    }

    @Override
    public int getCount() {
        return aReports.size();
    }

    @Nullable
    @Override
    public Report getItem(int position) {
        return aReports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Report report = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item,
                    parent, false);
        }
        TextView reportedBy = convertView.findViewById(R.id.reported_by_name);
        TextView reported = convertView.findViewById(R.id.reported_name);
        TextView description = convertView.findViewById(R.id.report_description);
        TextView reportTime = convertView.findViewById(R.id.report_time);
        TextView reportStatus = convertView.findViewById(R.id.reported_status);
        Button btnAccept = convertView.findViewById(R.id.accept_report_button);
        Button btnDecline = convertView.findViewById(R.id.decline_report_button);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (report != null) {
            reportedBy.setText(report.getReportingName());
            reported.setText(report.getReportedName());
            description.setText(report.getDescription());
            reportTime.setText(sdf.format(report.getReportTime()));
            reportStatus.setText(report.getStatus().toString());
            View finalConvertView = convertView;
            btnAccept.setOnClickListener(c -> {
                AlertDialog.Builder dialog = new AlertDialog.Builder(finalConvertView.getContext());
                LayoutInflater inflater = LayoutInflater.from(finalConvertView.getContext());
                View dialogView = inflater.inflate(R.layout.report_dialog, null);

                final EditText editTextInput = dialogView.findViewById(R.id.editTextInput);

                dialog.setMessage("Type explanation...")
                        .setCancelable(false)
                        .setView(dialogView)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String inputText = editTextInput.getText().toString();
                                Log.v("REZ", "Entered text: " + inputText);

                                Notification notification = new Notification();
                                notification.setDescription(inputText);
                                if(report.getTypeReported().equals("organizator")){
                                    notification.setEmail("milibovan190d@gmail.com");
                                } else {
                                    notification.setEmail("owner@gmail.com");
                                }
                                notification.setNotificationStatus(NotificationStatus.UNREAD);
                                notification.setNotificationType(USER_BLOCKED);

                                Notification notificationEmployee = new Notification();
                                notificationEmployee.setDescription(inputText);
                                if (report.getTypeReported().equals("owner")){
                                    notificationEmployee.setEmail("milibovan190d@gmail.com");
                                    notificationEmployee.setNotificationType(RESERVATION_CANCELED_OWNER);
                                } else if (report.getTypeReported().equals("organizator")) {
                                    notificationEmployee.setEmail("staff@gmail.com");
                                    notificationEmployee.setNotificationType(RESERVATION_CANCELED);
                                }
                                notificationEmployee.setNotificationStatus(NotificationStatus.UNREAD);

                                CloudStoreUtil.insertNotification(notification);
                                CloudStoreUtil.insertNotification(notificationEmployee);

                                if(report.getTypeReported().equals("organizator")){
                                    CloudStoreUtil.cancelReservations("milibovan190d@gmail.com");
                                } else if (report.getTypeReported().equals("owner")) {
                                    CloudStoreUtil.cancelReservations("owner@gmail.com");
                                }
                                Toast.makeText(finalConvertView.getContext(), "Report accepted!", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = dialog.create();
                alert.show();

                report.setStatus(ReportStatus.ACCEPTED);
                CloudStoreUtil.updateReportStatus(report);
            });
            btnDecline.setOnClickListener(c -> {
                report.setStatus(ReportStatus.DECLINED);
                CloudStoreUtil.updateReportStatus(report);
                Toast.makeText(finalConvertView.getContext(), "Report declined!", Toast.LENGTH_SHORT).show();
            });
            reportedBy.setOnClickListener(v -> {
                NavController navController = Navigation.findNavController(finalConvertView);
                Bundle bundle = new Bundle();
                bundle.putString("id", report.getIdReportingUser());
                bundle.putString("type", report.getTypeReporting());
                bundle.putBoolean("isVisible", false);
                navController.navigate(R.id.nav_user_profile, bundle);

            });
            reported.setOnClickListener(v -> {
                NavController navController = Navigation.findNavController(finalConvertView);
                Bundle bundle = new Bundle();
                bundle.putString("id", report.getIdReportedUser());
                bundle.putString("type", report.getTypeReported());
                bundle.putBoolean("isVisible", false);
                navController.navigate(R.id.nav_user_profile, bundle);
            });
        }

        return convertView;
    }

}