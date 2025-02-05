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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.Guest;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateGuestListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateGuestListFragment extends Fragment {

    public static ArrayList<Guest> guests = new ArrayList<>();

    private NavController navController;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateGuestListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateGuestListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateGuestListFragment newInstance(String param1, String param2) {
        CreateGuestListFragment fragment = new CreateGuestListFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_guest_list, container, false);

        EditText etGuestName = view.findViewById(R.id.etGuestName);
        Spinner spinnerAgeGroup = view.findViewById(R.id.spinnerAgeGroup);
        RadioGroup rgInvited = view.findViewById(R.id.rgInvited);
        RadioGroup rgAccepted = view.findViewById(R.id.rgAccepted);
        RadioGroup rgDietaryPreferences = view.findViewById(R.id.rgDietaryPreferences);
        Button btnAddGuest = view.findViewById(R.id.btnAddGuest);
        Button navButton = view.findViewById(R.id.btnSeeGuestList);

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main_org);
                navController.navigate(R.id.nav_guest_view);
            }
        });

        btnAddGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guestName = etGuestName.getText().toString();
                String ageGroup = spinnerAgeGroup.getSelectedItem().toString();
                boolean invited = rgInvited.getCheckedRadioButtonId() == R.id.rbInvitedYes;
                boolean accepted = rgAccepted.getCheckedRadioButtonId() == R.id.rbAcceptedYes;
                String specialRequests = getSelectedDietaryPreference(rgDietaryPreferences);
                String id = generateRandomId();

                Guest guest = new Guest(id, guestName, ageGroup, invited, accepted, specialRequests);

                CloudStoreUtil.insertGuest(guest);


                // Clear fields after adding guest
                etGuestName.setText("");
                spinnerAgeGroup.setSelection(0);
                rgInvited.clearCheck();
                rgAccepted.clearCheck();
                rgDietaryPreferences.clearCheck();
            }
        });


        return view;
    }

    private String getSelectedDietaryPreference(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = radioGroup.findViewById(selectedId);
            if (radioButton != null) {
                return radioButton.getText().toString();
            }
        }
        return "";
    }
    public static String generateRandomId() {
        // Generate a random UUID
        UUID uuid = UUID.randomUUID();
        // Convert UUID to String and remove hyphens
        String randomId = uuid.toString().replace("-", "");
        // Return the random ID
        return randomId;
    }
}