package com.example.eventplannerapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.eventplannerapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class AddSubcategoryActivity extends AppCompatActivity {
    private List<String> groupTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subcategory);

        groupTitles = new ArrayList<>();
        groupTitles.add("Service");
        groupTitles.add("Product");

        Button categoryButton = findViewById(R.id.categoryButton);
        PopupMenu popup = new PopupMenu(AddSubcategoryActivity.this, categoryButton);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.categories_menu_item, popup.getMenu());


        Button addSubcategoryButton = findViewById(R.id.addCategoryButton);
        addSubcategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateInputs()){
                    Intent intent = new Intent(AddSubcategoryActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                }
            }
            private boolean validateInputs() {
                TextInputLayout nameLayout = findViewById(R.id.nameInput);
                TextInputEditText name = (TextInputEditText) nameLayout.getEditText();
                TextInputLayout descriptionLayout = findViewById(R.id.descriptionInput);
                TextInputEditText description = (TextInputEditText) descriptionLayout.getEditText();
                RadioButton productButton = findViewById(R.id.productButton);
                RadioButton serviceButton = findViewById(R.id.serviceButton);

                if(String.valueOf(name).isEmpty() || String.valueOf(description).isEmpty()){
                    showToast("Please fill all the fields");
                    return false;
                }
                if(!productButton.isChecked() && !serviceButton.isChecked()){
                    showToast("Please set the type");
                    return false;
                }

                showToast(String.format("Subcategory %s successfully added.", name));
                return true;
            }
        });

        Menu menu = popup.getMenu();
        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    }

        private void showToast(String message) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

}