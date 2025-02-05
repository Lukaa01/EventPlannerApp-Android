package com.example.eventplannerapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.FragmentTransaction;
import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.OnNotificationSelectedListener;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.services.ShakeDetector;
import com.example.eventplannerapp.adapters.NotificationListAdapter;
import com.example.eventplannerapp.databinding.FragmentNotificationsViewBinding;
import com.example.eventplannerapp.model.Notification;
import java.util.ArrayList;

public class NotificationsView extends Fragment implements OnNotificationSelectedListener {

    private static final String TAG = "NotificationsView";
    public static ArrayList<Notification> notifications = new ArrayList<>();
    private FragmentNotificationsViewBinding binding;
    private NotificationListAdapter adapter;
    private Notification selectedNotification;
    private ArrayList<Notification> allNotifications;
    private SensorManager sensorManager;
    private ShakeDetector shakeDetector;
    private NotificationFilterType currentFilter = NotificationFilterType.ALL;

    public static NotificationsView newInstance(String param1, String param2) {
        return new NotificationsView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(count -> {
            switch (currentFilter) {
                case ALL:
                    currentFilter = NotificationFilterType.UNREAD;
                    break;
                case UNREAD:
                    currentFilter = NotificationFilterType.READ;
                    break;
                case READ:
                    currentFilter = NotificationFilterType.ALL;
                    break;
            }
            filterNotifications(currentFilter);
        });
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        sensorManager.unregisterListener(shakeDetector);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (allNotifications == null) {
            allNotifications = new ArrayList<>();
            notifications = new ArrayList<>();
        }

        binding = FragmentNotificationsViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String activityName = getActivity().getClass().getSimpleName();
        switch (activityName) {
            case "VlasnikHomeActivity":
                prepareNotificationList("owner@gmail.com");
                break;
            case "OrganizatorHomeActivity":
                prepareNotificationList("milibovan190d@gmail.com");
                break;
            case "AdminHomeActivity":
                prepareNotificationList("admin@gmail.com");
                break;
        }

        adapter = new NotificationListAdapter(getContext(), notifications);
        adapter.setOnNotificationSelectedListener(notification -> {
            selectedNotification = notification;
        });

        Button filterButton = root.findViewById(R.id.filter_button);
        filterButton.setOnClickListener(view -> showFilterDialog());

        return root;
    }

    private void showFilterDialog() {
        String[] filterOptions = {"All", "Read", "Unread"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Filter Notifications")
                .setItems(filterOptions, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            filterNotifications(NotificationFilterType.ALL);
                            break;
                        case 1:
                            filterNotifications(NotificationFilterType.READ);
                            break;
                        case 2:
                            filterNotifications(NotificationFilterType.UNREAD);
                            break;
                    }
                });
        builder.create().show();
    }

    private void prepareNotificationList(String email) {
        CloudStoreUtil.getAllNotifications(email).thenAccept(notificationList -> {
            notifications.clear();
            notifications.addAll(notificationList);
            allNotifications.clear();
            allNotifications.addAll(notificationList);
            generateListFragment();
        }).exceptionally(exception -> {
            return null;
        });

        Log.i("REZ_DB", "SELECT DATA");
    }

    private void generateListFragment() {
        NotificationListFragment notificationListFragment = NotificationListFragment.newInstance(notifications, this);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.scroll_notifications_list, notificationListFragment).commit();
    }

    @Override
    public void onNotificationSelected(Notification notification) {
        selectedNotification = notification;
    }

    private void filterNotifications(NotificationFilterType filterType) {
        ArrayList<Notification> filteredNotifications = new ArrayList<>();
        Log.i("NOTIF FILTERING", "Filter type is set to: " + filterType.name());
        Log.i("NOTIF FILTERING", "Starting allNotifications: " + allNotifications.size());
        if (allNotifications == null) return;
        for (Notification notification : allNotifications) {
            if (filterType == NotificationFilterType.ALL) {
                filteredNotifications.add(notification);
            } else if (filterType == NotificationFilterType.READ && notification.isRead()) {
                filteredNotifications.add(notification);
            } else if (filterType == NotificationFilterType.UNREAD && !notification.isRead()) {
                filteredNotifications.add(notification);
            }
        }
        Log.i("NOTIF FILTERING", "After filteredNotifications size: " + filteredNotifications.size());

        notifications.clear();
        notifications.addAll(filteredNotifications);
        generateListFragment();
        adapter.updateNotifications(filteredNotifications);
    }
}
