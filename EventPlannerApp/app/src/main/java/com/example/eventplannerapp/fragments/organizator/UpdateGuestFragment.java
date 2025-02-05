package com.example.eventplannerapp.fragments.organizator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.Guest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateGuestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateGuestFragment extends Fragment {

    private EditText etGuestName;
    private Spinner spinnerAgeGroup;
    private Switch switchInvited;
    private Switch switchAccepted;
    private Spinner spinnerSpecialRequests;
    private Button btnSave;
    private Guest guest;
    private NavController navController;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateGuestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateGuestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateGuestFragment newInstance(Guest guest) {
        UpdateGuestFragment fragment = new UpdateGuestFragment();
        Bundle args = new Bundle();
        args.putParcelable("guest", guest);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            guest = getArguments().getParcelable("guest");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_guest, container, false);

        etGuestName = view.findViewById(R.id.etGuestName);
        spinnerAgeGroup = view.findViewById(R.id.spinnerAgeGroup);
        switchInvited = view.findViewById(R.id.switchInvited);
        switchAccepted = view.findViewById(R.id.switchAccepted);
        spinnerSpecialRequests = view.findViewById(R.id.spinnerSpecialRequest);
        btnSave = view.findViewById(R.id.btnSave);

        // Set up spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.age_groups, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgeGroup.setAdapter(adapter);

        ArrayAdapter<CharSequence> specialRequestAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.special_requests, android.R.layout.simple_spinner_item);
        specialRequestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialRequests.setAdapter(specialRequestAdapter);

        if (guest != null) {
            etGuestName.setText(guest.getGuestName());
            spinnerAgeGroup.setSelection(getSpinnerIndex(spinnerAgeGroup, guest.getAgeGroup()));
            switchInvited.setChecked(guest.isInvited());
            switchAccepted.setChecked(guest.isAccepted());
            spinnerSpecialRequests.setSelection(getSpinnerIndex(spinnerSpecialRequests, guest.getSpecialRequests()));
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    guest.setGuestName(etGuestName.getText().toString());
                    guest.setAgeGroup(spinnerAgeGroup.getSelectedItem().toString());
                    guest.setInvited(switchInvited.isChecked());
                    guest.setAccepted(switchAccepted.isChecked());
                    guest.setSpecialRequests(spinnerSpecialRequests.getSelectedItem().toString());

                    CloudStoreUtil.updateGuest(guest);

                    navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main_org);
                    navController.navigate(R.id.nav_guest_view);
                }
            }
        });

        return view;
    }

    private int getSpinnerIndex(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                return i;
            }
        }
        return 0; // Default to the first item if not found
    }

    private boolean validateInput() {
        if (TextUtils.isEmpty(etGuestName.getText())) {
            etGuestName.setError("Name is required");
            return false;
        }
        if (spinnerAgeGroup.getSelectedItemPosition() == 0) { // Assuming the first item is a hint
            Toast.makeText(getContext(), "Please select an age group", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}

