package com.example.eventplannerapp.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.NotificationStatus;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.ReportStatus;
import com.example.eventplannerapp.activities.LoginActivity;
import com.example.eventplannerapp.activities.OrganizatorHomeActivity;
import com.example.eventplannerapp.model.Notification;
import com.example.eventplannerapp.model.Owner;
import com.example.eventplannerapp.model.Report;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VlasnikProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VlasnikProfileFragment extends Fragment {

    private EditText editOwnerName;
    private EditText editOwnerSurname;
    private EditText editOwnerEmail;
    private EditText editOwnerAddress;
    private EditText editOwnerPhone;

    private EditText editCompanyName;
    private EditText editCompanyDescription;
    private EditText editCompanyLocation;
    private EditText editCompanyPhone;
    private EditText editCompanyEmail;

    private Button btnSave;

    private Button btnDeactive;

    private Button btnChangePassword;


    private Owner owner;
    private NavController navController;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String NEW_REPORT = "NEW_REPORT";
    private static final String TERM_STARTING = "TERM_STARTING";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VlasnikProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VlasnikProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VlasnikProfileFragment newInstance(String param1, String param2) {
        VlasnikProfileFragment fragment = new VlasnikProfileFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vlasnik_profile, container, false);

        editOwnerName = view.findViewById(R.id.edit_owner_name);
        editOwnerSurname = view.findViewById(R.id.edit_owner_surname);
        editOwnerEmail = view.findViewById(R.id.edit_owner_email);
        editOwnerAddress = view.findViewById(R.id.edit_owner_address);
        editOwnerPhone = view.findViewById(R.id.edit_owner_phone);
        btnSave = view.findViewById(R.id.btn_save);
        btnChangePassword = view.findViewById(R.id.btn_change_password);
        btnDeactive = view.findViewById(R.id.btn_deactivate_account);

        editCompanyName = view.findViewById(R.id.edit_company_name);
        editCompanyDescription = view.findViewById(R.id.edit_company_description);
        editCompanyLocation = view.findViewById(R.id.edit_company_location);
        editCompanyPhone = view.findViewById(R.id.edit_company_phone);
        editCompanyEmail = view.findViewById(R.id.edit_company_email);

        editCompanyName.setText("Kompanijaa");
        editCompanyEmail.setText("company@example.com");

        // Unapred popuniti vrednosti za ostala polja ako je potrebno
        editCompanyDescription.setText("Opis kompanije");
        editCompanyLocation.setText("Lokacija kompanije");
        editCompanyPhone.setText("123-456-7890");


        String email = "josejosemou8@gmail.com";
        CloudStoreUtil.selectOwnerByEmail(email).thenAccept(companyOwner -> {
            if (companyOwner != null) {
                owner = companyOwner;
                editOwnerName.setText(companyOwner.getFirstname());
                editOwnerSurname.setText(companyOwner.getLastname());
                editOwnerEmail.setText(companyOwner.getEmail());
                editOwnerPhone.setText(companyOwner.getPhone());
                editOwnerAddress.setText(companyOwner.getAddress());

                // Uslov za dozvolu editovanja
                boolean canEdit = checkEditCondition();
                enableEditing(canEdit);

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                owner.setFirstname(editOwnerName.getText().toString());
                owner.setLastname(editOwnerSurname.getText().toString());
                owner.setEmail(editOwnerEmail.getText().toString());
                owner.setPhone(editOwnerPhone.getText().toString());
                owner.setAddress(editOwnerAddress.getText().toString());


                CloudStoreUtil.updateOwner(owner);

            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main_org);
                navController.navigate(R.id.nav_changePassword);
            }
        });

        Button reportButton = view.findViewById(R.id.report_user_button);

        reportButton.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater1 = LayoutInflater.from(getActivity());
            View dialogView = inflater1.inflate(R.layout.report_dialog, null);

            final EditText editTextInput = dialogView.findViewById(R.id.editTextInput);

            dialog.setMessage("Type explanation...")
                    .setCancelable(false)
                    .setView(dialogView)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String inputText = editTextInput.getText().toString();
                            Log.v("REZ", "Entered text: " + inputText);

                            Report report = new Report();
                            report.setIdReportingUser("O2TzLAUyHDR9yQWSljiJ");
                            report.setTypeReporting("organizator");
                            report.setReportingName("Mili Bovan");
                            report.setReportTime(new Date());
                            report.setStatus(ReportStatus.REPORTED);
                            report.setIdReportedUser(owner.getId());
                            report.setTypeReported("owner");
                            report.setReportedName(owner.getFirstname() + " " + owner.getLastname());
                            report.setDescription(inputText);

                            CloudStoreUtil.insertReport(report);
                            Toast.makeText(getContext(), "User reported!", Toast.LENGTH_SHORT).show();

                            Notification notification = new Notification();
                            notification.setEmail("admin@gmail.com");
                            notification.setDescription(inputText);
                            notification.setNotificationType(NEW_REPORT);
                            notification.setNotificationStatus(NotificationStatus.UNREAD);

                            CloudStoreUtil.insertNotification(notification);
                        }
                    });
            AlertDialog alert = dialog.create();
            alert.show();

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

    private boolean checkEditCondition() {
        String activityName = getActivity().getClass().getSimpleName();
        if (activityName.equals("VlasnikHomeActivity")) {
            return true;
        }
        return false; // Zamenite sa stvarnim uslovom
    }

    private void enableEditing(boolean canEdit) {
        editOwnerName.setEnabled(canEdit);
        editOwnerSurname.setEnabled(canEdit);
        editOwnerEmail.setEnabled(canEdit);
        editOwnerPhone.setEnabled(canEdit);
        editOwnerAddress.setEnabled(canEdit);
        btnSave.setVisibility(canEdit ? View.VISIBLE : View.GONE);
        btnChangePassword.setVisibility(canEdit ? View.VISIBLE : View.GONE);
        editCompanyDescription.setEnabled(canEdit);
        editCompanyPhone.setEnabled(canEdit);
        editCompanyLocation.setEnabled(canEdit);



        if (canEdit) {
            Toast.makeText(getContext(), "Editovanje je omogućeno", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Editovanje nije dozvoljeno", Toast.LENGTH_SHORT).show();
        }

    }
}