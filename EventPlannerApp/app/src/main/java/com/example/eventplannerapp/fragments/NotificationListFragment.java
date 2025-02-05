package com.example.eventplannerapp.fragments;

import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.eventplannerapp.OnNotificationSelectedListener;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.adapters.NotificationListAdapter;
import com.example.eventplannerapp.model.Notification;
import java.util.ArrayList;

public class NotificationListFragment extends ListFragment {

    private NotificationListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<Notification> mNotifications;
    private OnNotificationSelectedListener mListener;

    public void setOnNotificationSelectedListener(OnNotificationSelectedListener listener) {
        mListener = listener;
    }

    public NotificationListFragment() {
    }

    public static NotificationListFragment newInstance(ArrayList<Notification> notifications, OnNotificationSelectedListener listener) {
        NotificationListFragment fragment = new NotificationListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, notifications);
        fragment.setArguments(args);
        fragment.setOnNotificationSelectedListener(listener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNotifications = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new NotificationListAdapter(getActivity(), mNotifications);
            adapter.setOnNotificationSelectedListener(notification -> {
                if (mListener != null) {
                    mListener.onNotificationSelected(notification);
                }
            });
            setListAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification_list, container, false);
    }

    public void updateAdapter(ArrayList<Notification> notifications) {
        adapter.updateNotifications(notifications);
    }
}
