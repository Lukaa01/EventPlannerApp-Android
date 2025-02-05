package com.example.eventplannerapp.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.EventType;
import com.example.eventplannerapp.viewmodels.EventCategoryViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventCategoryFragment extends DialogFragment {
    private LinearLayout fragmentContainer;
    private FirebaseFirestore db;
    private OnSelectedEventsListener listener;
    private EventCategoryViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_category, container, false);
        fragmentContainer = view.findViewById(R.id.fragment_container);
        db = FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(EventCategoryViewModel.class);

        fetchEventTypes();

        MaterialButton confirmButton = view.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onEventsSelected(viewModel.getSelectedEvents());
                }
                dismiss();
            }
        });

        MaterialButton cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.removeAll();
                dismiss();
            }
        });
    }

    private void fetchEventTypes() {
        db.collection("eventTypes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Map<String, List<String>> eventTypeMap = new HashMap<>();
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                EventType eventType = document.toObject(EventType.class);
                                eventTypeMap.computeIfAbsent(eventType.getEventClass(), k -> new ArrayList<>()).add(eventType.getName());
                            }
                        }
                        displayEventTypes(eventTypeMap);
                    } else {
                        // Handle possible errors.
                    }
                });
    }

    private void displayEventTypes(Map<String, List<String>> eventTypeMap) {
        fragmentContainer.removeAllViews(); // Clear existing views

        List<String> selectedEvents = viewModel.getSelectedEvents();

        for (Map.Entry<String, List<String>> entry : eventTypeMap.entrySet()) {
            String eventClass = entry.getKey();
            List<String> eventNames = entry.getValue();

            MaterialTextView eventClassTextView = new MaterialTextView(getContext());
            eventClassTextView.setText(eventClass);
            eventClassTextView.setTextSize(20);
            eventClassTextView.setTypeface(null, Typeface.BOLD);
            eventClassTextView.setTextColor(getResources().getColor(R.color.white));
            eventClassTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            eventClassTextView.setPadding(0, 0, 0, 16);
            fragmentContainer.addView(eventClassTextView);

            for (String eventName : eventNames) {
                MaterialCheckBox checkBox = new MaterialCheckBox(getContext());
                checkBox.setText(eventName);
                checkBox.setTextColor(getResources().getColor(R.color.white));
                checkBox.setChecked(selectedEvents.contains(eventName)); // Set checked state
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            viewModel.addEvent(eventName);
                        } else {
                            viewModel.removeEvent(eventName);
                        }
                    }
                });
                fragmentContainer.addView(checkBox);
            }
        }
    }

    public void setSelectedEventsListener(OnSelectedEventsListener listener) {
        this.listener = listener;
    }

    public interface OnSelectedEventsListener {
        void onEventsSelected(List<String> selectedEvents);
    }
}
