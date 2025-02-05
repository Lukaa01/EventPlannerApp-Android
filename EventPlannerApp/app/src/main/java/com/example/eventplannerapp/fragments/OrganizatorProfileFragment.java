package com.example.eventplannerapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.LoginActivity;
import com.example.eventplannerapp.model.EventOrganizer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrganizatorProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrganizatorProfileFragment extends Fragment {
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhoneNumber;
    private Button btnSave;
    private Button btnDeactive;


    private Button btnChangePassword;

    private EventOrganizer organizer;
    private NavController navController;

    private TextView tvEmail;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrganizatorProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrganizatorProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrganizatorProfileFragment newInstance(String param1, String param2) {
        OrganizatorProfileFragment fragment = new OrganizatorProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_organizator_profile, container, false);


        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etPhoneNumber = view.findViewById(R.id.etPhoneNumber);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnSave = view.findViewById(R.id.btnSaveProfile);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnDeactive = view.findViewById(R.id.btn_deactivate_account);

        String email = "milibovan190d@gmail.com";
        CloudStoreUtil.selectEventOrganizerByEmail(email).thenAccept( eventOrganizer -> {
            if (eventOrganizer != null) {
                organizer = eventOrganizer;
                // Postavi podatke o korisniku u polja
                etFirstName.setText(eventOrganizer.getFirstname());
                etLastName.setText(eventOrganizer.getLastname());
                tvEmail.setText(eventOrganizer.getEmail());
                etPhoneNumber.setText(eventOrganizer.getPhone());
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                organizer.setFirstname(etFirstName.getText().toString());
                organizer.setLastname(etLastName.getText().toString());
                organizer.setPhone(etPhoneNumber.getText().toString());

                CloudStoreUtil.updateOrganizer(organizer);
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main_org);
                navController.navigate(R.id.nav_changePassword);
            }
        });
        btnDeactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}