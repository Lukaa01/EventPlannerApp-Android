package com.example.eventplannerapp.fragments.vlasnik;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.NotificationStatus;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.ReportStatus;
import com.example.eventplannerapp.model.EventOrganizer;
import com.example.eventplannerapp.model.Notification;
import com.example.eventplannerapp.model.Owner;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Report;

import java.util.Date;

public class UserProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "type";
    private static final String IS_BUTTON_VISIBLE = "isVisible";
    private static final String NEW_REPORT = "NEW_REPORT";

    private String id;
    private String type;
    private boolean isVisible;
    private String inputText;
    private Owner owner;
    private EventOrganizer organizer;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    public static UserProfileFragment newInstance(String id, String type, boolean visibility) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, type);
        args.putBoolean(IS_BUTTON_VISIBLE, visibility);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARG_PARAM1);
            type = getArguments().getString(ARG_PARAM2);
            isVisible = getArguments().getBoolean(IS_BUTTON_VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        if (type.equals("owner")) {
            CloudStoreUtil.selectOwnerById(id).thenAccept(o -> {
                if (o != null) {
                    owner = o;
                    TextView email = view.findViewById(R.id.emailTextView);
                    email.setText(String.valueOf(o.getEmail()));
                    TextView firstname = view.findViewById(R.id.firstnameTextView);
                    firstname.setText(String.valueOf(o.getFirstname()));
                    TextView lastname = view.findViewById(R.id.lastnameTextView);
                    lastname.setText(String.valueOf(o.getLastname()));
                    TextView address = view.findViewById(R.id.addressTextView);
                    address.setText(String.valueOf(o.getAddress()));
                    TextView phone = view.findViewById(R.id.phoneTextView);
                    phone.setText(String.valueOf(o.getPhone()));
                }
            });
        } else if (type.equals("organizator")) {
            CloudStoreUtil.selectEventOrganizerById(id).thenAccept(eo -> {
                if (eo != null) {
                    organizer = eo;
                    TextView email = view.findViewById(R.id.emailTextView);
                    email.setText(String.valueOf(eo.getEmail()));
                    TextView firstname = view.findViewById(R.id.firstnameTextView);
                    firstname.setText(String.valueOf(eo.getFirstname()));
                    TextView lastname = view.findViewById(R.id.lastnameTextView);
                    lastname.setText(String.valueOf(eo.getLastname()));
                    TextView address = view.findViewById(R.id.addressTextView);
                    address.setText(String.valueOf(eo.getAddress()));
                    TextView phone = view.findViewById(R.id.phoneTextView);
                    phone.setText(String.valueOf(eo.getPhone()));
                }
            });
        }
        return view;
    }
}
