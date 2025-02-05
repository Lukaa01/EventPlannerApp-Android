package com.example.eventplannerapp.fragments;// CategoryFragment.java
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.eventplannerapp.R;

public class CategoryFragment extends Fragment {

    private String categoryName;
    private Button editButton;
    private Button deleteButton;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        // Find views
        TextView categoryNameTextView = view.findViewById(R.id.text_category_name);
        editButton = view.findViewById(R.id.button_edit);
        deleteButton = view.findViewById(R.id.button_delete);

        // Set category name
        categoryNameTextView.setText(categoryName);

        // Set click listeners
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Update Category");

                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText inputName = new EditText(getContext());
                inputName.setHint("New Name");
                inputName.setInputType(InputType.TYPE_CLASS_TEXT);
                layout.addView(inputName);

                final EditText inputDescription = new EditText(getContext());
                inputDescription.setHint("New Description");
                inputDescription.setInputType(InputType.TYPE_CLASS_TEXT);
                layout.addView(inputDescription);

                builder.setView(layout);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = inputName.getText().toString();
                        String newDescription = inputDescription.getText().toString();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                        positiveButton.setTextColor(getResources().getColor(R.color.white));
                        positiveButton.setBackgroundColor(getResources().getColor(R.color.primary));

                        Button negativeButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                        negativeButton.setTextColor(getResources().getColor(R.color.white));
                        negativeButton.setBackgroundColor(getResources().getColor(R.color.primary));
                    }
                });

                dialog.show();


            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Category");
                builder.setMessage("Are you sure you want to delete this category?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteButton.setEnabled(false);
                        deleteButton.setBackgroundColor(getResources().getColor(R.color.black));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        return view;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // Add any other methods you need
}
