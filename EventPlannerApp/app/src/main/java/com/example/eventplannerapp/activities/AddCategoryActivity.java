package com.example.eventplannerapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eventplannerapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddCategoryActivity extends AppCompatActivity {
    private TextInputEditText nameInput;
    private TextInputEditText descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        TextInputLayout descriptionInputLayout = findViewById(R.id.descriptionInput);
        TextInputLayout nameInputLayout = findViewById(R.id.nameInput);
        nameInput = (TextInputEditText) nameInputLayout.getEditText();
        descriptionInput = (TextInputEditText) descriptionInputLayout.getEditText();


        Button addCategoryButton = findViewById(R.id.addCategoryButton);

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInputs()){
                    Intent intent = new Intent(AddCategoryActivity.this, AdminHomeActivity.class);
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

        String message = String.format("Category with name %s is created", name);
        showToast(message);
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}