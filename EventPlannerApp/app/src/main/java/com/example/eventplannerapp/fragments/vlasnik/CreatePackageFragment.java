package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePackageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePackageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param1";

    public CreatePackageFragment() {
        // Required empty public constructor
    }

    public static CreatePackageFragment newInstance(String param) {
        CreatePackageFragment fragment = new CreatePackageFragment();
        Bundle args = new Bundle();
        args.putString(param, "PARAM");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_package, container, false);
    }
}