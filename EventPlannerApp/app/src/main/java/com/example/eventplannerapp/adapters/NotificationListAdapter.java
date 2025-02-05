package com.example.eventplannerapp.adapters;

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
import com.example.eventplannerapp.model.Notification;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class NotificationListAdapter extends ArrayAdapter<Notification> {

    private ArrayList<Notification> aNotifications;
    private Consumer<Notification> mListener;

    public NotificationListAdapter(Context context, ArrayList<Notification> notifications) {
        super(context, R.layout.notification_card, notifications);
        aNotifications = notifications;
    }

    public void updateNotifications(ArrayList<Notification> newNotifications) {
        this.aNotifications.clear();
        this.aNotifications.addAll(newNotifications);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() { return aNotifications.size(); }

    @Nullable
    @Override
    public Notification getItem(int position) { return aNotifications.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @NotNull
    @Override
    public View getView(int position, @Nullable View convertView, @NotNull ViewGroup parent) {
        Notification notification = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_card, parent, false);
        }
        LinearLayout notificationCard = convertView.findViewById(R.id.notification_card_item);
        TextView notificationId = convertView.findViewById(R.id.notification_id);
        TextView notificationDescription = convertView.findViewById(R.id.notification_description);
        TextView notificationEmail = convertView.findViewById(R.id.notification_email);
        TextView notificationType = convertView.findViewById(R.id.notification_type);
        TextView notificationStatus = convertView.findViewById(R.id.notification_status);

        if (notification != null) {
            notificationId.setText(notification.getId());
            notificationDescription.setText(notification.getDescription());
            notificationEmail.setText(notification.getEmail());
            notificationType.setText(notification.getNotificationType());
            notificationStatus.setText(notification.getNotificationStatus().toString());

            notificationCard.setOnClickListener(v -> {
                Log.i("EventPlannerApp", "Clicked: Notification ID: " + notification.getId());
                Toast.makeText(getContext(), "Clicked notification ID: " + notification.getId() , Toast.LENGTH_SHORT).show();
                if (mListener != null) {
                    mListener.accept(notification);
                }
            });
        }
        return convertView;
    }

    public void setOnNotificationSelectedListener(Consumer<Notification> listener) { mListener = listener; }
}
