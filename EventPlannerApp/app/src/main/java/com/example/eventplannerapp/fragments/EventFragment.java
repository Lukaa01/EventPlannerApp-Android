package com.example.eventplannerapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.AddEventTypeActivity;

public class EventFragment extends Fragment {

    private String categoryName;
    Button editButton;
    Button declineButton;

    public EventFragment(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        com.google.android.material.textview.MaterialTextView categoryNameTextView = view.findViewById(R.id.text_category_name);
        categoryNameTextView.setText(categoryName);

        editButton = view.findViewById(R.id.button_edit);
        declineButton = view.findViewById(R.id.button_decline);
        final Context context = getContext();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Edit Category");

                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText inputDescription = new EditText(getContext());
                inputDescription.setHint("New Description");
                inputDescription.setInputType(InputType.TYPE_CLASS_TEXT);
                layout.addView(inputDescription);

                Button editCategoryButton = new Button(getContext());
                PopupMenu popup = new PopupMenu(context, editCategoryButton);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.categories_menu_item, popup.getMenu());
                editCategoryButton.setText("Edit Category");
                editCategoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                item.setChecked(!item.isChecked());
                                return true;
                            }
                        });
                        popup.show();
                    }
                });
                layout.addView(editCategoryButton);

                builder.setView(layout);        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newDescription = inputDescription.getText().toString();
/*                    // Ovdje postavite novi opis kategorije
                    setCategoryDescription(newDescription);*/
                }
            });
            builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getContext());
                    confirmBuilder.setTitle("Are you sure?");
                    confirmBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Ako je korisnik siguran, onemogućite dugme i promijenite boju
                            editButton.setEnabled(false);
                            editButton.setBackgroundColor(getResources().getColor(R.color.grey));
                        }
                    });
                    confirmBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog confirmDialog = confirmBuilder.create();
                    confirmDialog.show();
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

declineButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Delete Category");
            builder.setMessage("Are you sure you want to delete this category?");

            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Ako je korisnik siguran, onemogućite dugme i promijenite boju
                    declineButton.setEnabled(false);
                    declineButton.setBackgroundColor(getResources().getColor(R.color.black));
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

}
