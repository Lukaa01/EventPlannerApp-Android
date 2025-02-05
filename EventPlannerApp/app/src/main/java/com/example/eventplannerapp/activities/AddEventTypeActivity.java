package com.example.eventplannerapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.eventplannerapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddEventTypeActivity extends AppCompatActivity {
    private TextInputEditText nameInput;
    private TextInputEditText descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_type);
        TextInputLayout descriptionInputLayout = findViewById(R.id.descriptionInput);
        TextInputLayout nameInputLayout = findViewById(R.id.nameInput);
        nameInput = (TextInputEditText) nameInputLayout.getEditText();
        descriptionInput = (TextInputEditText) descriptionInputLayout.getEditText();

        Button selectType = findViewById(R.id.fragment_container);
        PopupMenu popup = new PopupMenu(AddEventTypeActivity.this, selectType);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.categories_menu_item, popup.getMenu());
        selectType.setOnClickListener(new View.OnClickListener() {
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

        Button addEventButton = findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInputs()){
                    Intent intent = new Intent(AddEventTypeActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    private boolean validateInputs() {
        String name = nameInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty()) {
            showToast("Please fill in all fields");
            return false;
        }

        String message = String.format("Event with name %s is created", name);
        showToast(message);
        return true;
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}