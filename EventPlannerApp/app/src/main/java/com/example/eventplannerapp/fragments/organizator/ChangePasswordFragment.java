package com.example.eventplannerapp.fragments.organizator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.EventOrganizer;
import com.example.eventplannerapp.model.Owner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {

    private EditText etOldPassword, etNewPassword, etConfirmNewPassword;
    private Button btnSavePassword;
    private NavController navController;
    private EventOrganizer organizer;
    private Owner owner;
    private String oldPasswordOrganizer;
    private String oldPasswordOwner;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        String activityName = getActivity().getClass().getSimpleName();

        if (activityName.equals("VlasnikHomeActivity")) {
            etOldPassword = view.findViewById(R.id.etOldPassword);
            etNewPassword = view.findViewById(R.id.etNewPassword);
            etConfirmNewPassword = view.findViewById(R.id.etConfirmNewPassword);
            btnSavePassword = view.findViewById(R.id.btnSavePassword);
            String email = "josejosemou8@gmail.com";
            CloudStoreUtil.selectOwnerByEmail(email).thenAccept(eventOwner -> {
                if (eventOwner != null) {
                    owner = eventOwner;
                    oldPasswordOwner = organizer.getPassword();
                    Log.d("Tag", oldPasswordOwner);
                }
            });
            btnSavePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Provera da li su sva polja popunjena
                    String oldPassword = etOldPassword.getText().toString().trim();
                    String newPassword = etNewPassword.getText().toString().trim();
                    String confirmNewPassword = etConfirmNewPassword.getText().toString().trim();


                    if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                        Toast.makeText(getContext(), "Sva polja moraju biti popunjena", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(!oldPassword.equals(oldPasswordOwner)) {
                        return;
                    }

                    // Provera da li se novi passwordi poklapaju
                    if (!newPassword.equals(confirmNewPassword)) {
                        Toast.makeText(getContext(), "Novi passwordi se ne poklapaju", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    owner.setPassword(newPassword);
                    Log.d("Tag", owner.getPassword());
                    // Ovde dodajte kod za proveru starog passworda i čuvanje novog passworda
                    CloudStoreUtil.updateOwner(owner);

                    navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main_org);
                    navController.navigate(R.id.nav_vlasnik_profile);
                }
            });


            return view;
        }

        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etConfirmNewPassword = view.findViewById(R.id.etConfirmNewPassword);
        btnSavePassword = view.findViewById(R.id.btnSavePassword);
        String email = "milibovan190d@gmail.com";
        CloudStoreUtil.selectEventOrganizerByEmail(email).thenAccept(eventOrganizer -> {
            if (eventOrganizer != null) {
                organizer = eventOrganizer;
                oldPasswordOrganizer = organizer.getPassword();
                Log.d("Tag", oldPasswordOrganizer);
            }
        });
        btnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Provera da li su sva polja popunjena
                String oldPassword = etOldPassword.getText().toString().trim();
                String newPassword = etNewPassword.getText().toString().trim();
                String confirmNewPassword = etConfirmNewPassword.getText().toString().trim();


                if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                    Toast.makeText(getContext(), "Sva polja moraju biti popunjena", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!oldPassword.equals(oldPasswordOrganizer)) {
                    return;
                }

                // Provera da li se novi passwordi poklapaju
                if (!newPassword.equals(confirmNewPassword)) {
                    Toast.makeText(getContext(), "Novi passwordi se ne poklapaju", Toast.LENGTH_SHORT).show();
                    return;
                }

                organizer.setPassword(newPassword);
                Log.d("Tag", organizer.getPassword());
                // Ovde dodajte kod za proveru starog passworda i čuvanje novog passworda
                CloudStoreUtil.updateOrganizer(organizer);

                navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main_org);
                navController.navigate(R.id.nav_org_profile);
            }
        });


        return view;
    }
}