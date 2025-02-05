package com.example.eventplannerapp.fragments.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.AddEventTypeActivity;
import com.example.eventplannerapp.fragments.EventCategoryFragment;

public class HandleEventsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_handle_events, container, false);

        Button addEventButton = view.findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddEventTypeActivity.class);
                startActivity(intent);
            }
        });

        getChildFragmentManager().beginTransaction().add(R.id.fragment_container, new EventCategoryFragment()).commit();
        getChildFragmentManager().beginTransaction().add(R.id.fragment_container, new EventCategoryFragment()).commit();
        getChildFragmentManager().beginTransaction().add(R.id.fragment_container, new EventCategoryFragment()).commit();
        getChildFragmentManager().beginTransaction().add(R.id.fragment_container, new EventCategoryFragment()).commit();
        getChildFragmentManager().beginTransaction().add(R.id.fragment_container, new EventCategoryFragment()).commit();
        getChildFragmentManager().beginTransaction().add(R.id.fragment_container, new EventCategoryFragment()).commit();
        getChildFragmentManager().beginTransaction().add(R.id.fragment_container, new EventCategoryFragment()).commit();

        return view;
    }
}
