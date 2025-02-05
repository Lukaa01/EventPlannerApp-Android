package com.example.eventplannerapp.activities;

import static android.service.controls.ControlsProviderService.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.fragments.EventCategoryFragment;
import com.example.eventplannerapp.model.Owner;
import com.example.eventplannerapp.model.WorkingHours;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OwnerRegistartionActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final int REQUEST_STORAGE_PERMISSION = 102;
    private Uri photoUri;
    private ImageView imageView;
    private FloatingActionButton floatingActionButton;
    private LinearLayout categoryContainer;
    private FirebaseFirestore db;
    private MaterialButton eventButton;
    public WorkingHours workingHours = new WorkingHours();
    private Button mondayTime;
    private TextView monday;
    private Button mondayEndTime;
    private TextView mondayEnd;

    private Button tuesdayTime;
    private TextView tuesday;
    private Button tuesdayEndTime;
    private TextView tuesdayEnd;

    private Button wendesdayTime;
    private TextView wendesday;
    private Button wendesdayEndTime;
    private TextView wendesdayEnd;

    private Button thursdayTime;
    private TextView thursday;
    private Button thursdayEndTime;
    private TextView thursdayEnd;

    private Button fridayTime;
    private TextView friday;
    private Button fridayEndTime;
    private TextView fridayEnd;

    private Button saturdayTime;
    private TextView saturday;
    private Button saturdayEndTime;
    private TextView saturdayEnd;

    private Button sundayTime;
    private TextView sunday;
    private Button sundayEndTime;
    private TextView sundayEnd;

    private MaterialCheckBox mondayOff;
    private MaterialCheckBox tuesdayOff;
    private MaterialCheckBox wendesdayOff;
    private MaterialCheckBox thursdayOff;
    private MaterialCheckBox fridayOff;
    private MaterialCheckBox saturdayOff;
    private MaterialCheckBox sundayOff;
    private Owner.OwnerType CompanyType;
    private Button signInButton;

    private List<String> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_registartion);

        TextInputLayout nameInput = findViewById(R.id.nameInput);
        TextInputLayout emailInput = findViewById(R.id.lastnameInput);
        TextInputLayout addressInput = findViewById(R.id.addressInput);
        TextInputLayout phoneInput = findViewById(R.id.phoneInput);
        TextInputLayout descriptionInput = findViewById(R.id.descriptionInput);

        TextInputEditText name = (TextInputEditText) nameInput.getEditText();
        TextInputEditText email = (TextInputEditText) emailInput.getEditText();
        TextInputEditText address = (TextInputEditText) addressInput.getEditText();
        TextInputEditText phone = (TextInputEditText) phoneInput.getEditText();
        TextInputEditText description = (TextInputEditText) descriptionInput.getEditText();

        RadioButton agencyRadioButton = findViewById(R.id.agencyRadioButton);
        RadioButton companyRadioButton = findViewById(R.id.companyRadioButton);
        RadioButton shopRadioButton = findViewById(R.id.shopRadioButton);
        imageView = findViewById(R.id.imageView);
        floatingActionButton = findViewById(R.id.selectImageButton);
        categoryContainer = findViewById(R.id.categoryContainer);
        eventButton = findViewById(R.id.eventsButton);
        db = FirebaseFirestore.getInstance();

        fetchCategories();

        mondayTime = findViewById(R.id.mondayTime);
        monday = findViewById(R.id.mondayTimeText);
        mondayEndTime = findViewById(R.id.mondayEndTime);
        mondayEnd = findViewById(R.id.mondayEndTimeText);

        tuesdayTime = findViewById(R.id.tuesdayTime);
        tuesday = findViewById(R.id.tuesdayTimeText);
        tuesdayEndTime = findViewById(R.id.tuesdayEndTime);
        tuesdayEnd = findViewById(R.id.tuesdayEndTimeText);

        wendesdayTime = findViewById(R.id.wendesdayTime);
        wendesday = findViewById(R.id.wendesdayTimeText);
        wendesdayEndTime = findViewById(R.id.wendesdayEndTime);
        wendesdayEnd = findViewById(R.id.wendesdayEndTimeText);

        thursdayTime = findViewById(R.id.thursdayTime);
        thursday = findViewById(R.id.thursdayTimeText);
        thursdayEndTime = findViewById(R.id.thursdayEndTime);
        thursdayEnd = findViewById(R.id.thursdayEndTimeText);

        fridayTime = findViewById(R.id.fridayTime);
        friday = findViewById(R.id.fridayTimeText);
        fridayEndTime = findViewById(R.id.fridayEndTime);
        fridayEnd = findViewById(R.id.fridayEndTimeText);

        saturdayTime = findViewById(R.id.saturdayTime);
        saturday = findViewById(R.id.saturdayTimeText);
        saturdayEndTime = findViewById(R.id.saturdayEndTime);
        saturdayEnd = findViewById(R.id.saturdayEndTimeText);

        sundayTime = findViewById(R.id.sundayTime);
        sunday = findViewById(R.id.sundayTimeText);
        sundayEndTime = findViewById(R.id.sundayEndTime);
        sundayEnd = findViewById(R.id.sundayEndTimeText);

        signInButton = findViewById(R.id.signinButton);

        mondayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OwnerRegistartionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                monday.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        mondayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OwnerRegistartionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mondayEnd.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });


        tuesdayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OwnerRegistartionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                tuesday.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        tuesdayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OwnerRegistartionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                tuesdayEnd.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        wendesdayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OwnerRegistartionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                wendesday.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        wendesdayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OwnerRegistartionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                wendesdayEnd.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });


        thursdayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OwnerRegistartionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                thursday.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        thursdayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OwnerRegistartionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                thursdayEnd.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        fridayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OwnerRegistartionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                friday.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        fridayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OwnerRegistartionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                fridayEnd.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });


        saturdayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OwnerRegistartionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                saturday.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        saturdayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OwnerRegistartionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                saturdayEnd.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        sundayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OwnerRegistartionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                sunday.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        sundayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(OwnerRegistartionActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                sundayEnd.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });


        mondayOff = findViewById(R.id.mondayOff);
        mondayOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mondayTime.setEnabled(!isChecked);
                mondayEndTime.setEnabled(!isChecked);
            }
        });
        tuesdayOff = findViewById(R.id.tuesdayOff);
        tuesdayOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                tuesdayTime.setEnabled(!isChecked);
                tuesdayEndTime.setEnabled(!isChecked);
            }
        });
        wendesdayOff = findViewById(R.id.wendesdayOff);
        wendesdayOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                wendesdayTime.setEnabled(!isChecked);
                wendesdayEndTime.setEnabled(!isChecked);
            }
        });
        thursdayOff = findViewById(R.id.thursdayOff);
        thursdayOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                thursdayTime.setEnabled(!isChecked);
                thursdayEndTime.setEnabled(!isChecked);
            }
        });
        fridayOff = findViewById(R.id.fridayOff);
        fridayOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                fridayTime.setEnabled(!isChecked);
                fridayEndTime.setEnabled(!isChecked);
            }
        });
        saturdayOff = findViewById(R.id.saturdayOff);
        saturdayOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                saturdayTime.setEnabled(!isChecked);
                saturdayEndTime.setEnabled(!isChecked);
            }
        });
        sundayOff = findViewById(R.id.sundayOff);
        sundayOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                sundayTime.setEnabled(!isChecked);
                sundayEndTime.setEnabled(!isChecked);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRequestPermission();
            }
        });

        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                EventCategoryFragment eventCategoryFragment = new EventCategoryFragment();
                eventCategoryFragment.show(fragmentManager, "eventCategoryFragment");

                eventCategoryFragment.setSelectedEventsListener(new EventCategoryFragment.OnSelectedEventsListener() {
                    @Override
                    public void onEventsSelected(List<String> selectedEvents) {
                        events = selectedEvents;
                    }
                });
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (agencyRadioButton.isChecked()){
                    CompanyType = Owner.OwnerType.AGENCY;
                } else if (companyRadioButton.isChecked()) {
                    CompanyType = Owner.OwnerType.COMPANY;
                } else if (shopRadioButton.isChecked()) {
                    CompanyType = Owner.OwnerType.STORE;
                } else {
                    CompanyType = Owner.OwnerType.ORGANIZATION;
                }

                Log.d("EventCategoryFragment", "Selected Events: " + events.toString());
                Gson gson = new Gson();
                String previousOwner = getIntent().getStringExtra("owner");
                Owner owner = gson.fromJson(previousOwner, Owner.class);
                owner.setCategories(getSelectedCategories());
                owner.setEventTypes(events);
                owner.setWorkingHours(getWorkingHours());
                owner.setCompanyEmail(email.getText().toString().trim());
                owner.setCompanyName(name.getText().toString().trim());
                owner.setCompanyAddress(address.getText().toString().trim());
                owner.setCompanyPhone(phone.getText().toString().trim());
                owner.setCompanyDescription(description.getText().toString().trim());
                owner.setCompanyPhotos((photoUri != null) ? photoUri.toString() : null);
                owner.setType(CompanyType);

                //TODO Add notification when implemented

                db.collection("ownerRegistrationRequests").document(owner.getId())
                        .set(owner)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                showToast("User added to temp collection for verification.");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showToast("Error adding user to temp collection. " + e);
                            }
                        });
                Intent intent = new Intent(OwnerRegistartionActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE) {
            photoUri = data.getData();
            imageView.setImageURI(photoUri);
        }
    }

    private void onRequestPermission() {
        ActivityCompat.requestPermissions(OwnerRegistartionActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        ActivityCompat.requestPermissions(OwnerRegistartionActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        startImagePicker();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION || requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onRequestPermission();
            } else {
                //Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startImagePicker() {
        ImagePicker.with(OwnerRegistartionActivity.this)
                .crop()                //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    private void fetchCategories() {
        db.collection("categories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                String categoryName = document.getString("name");
                                addCategoryCheckbox(categoryName);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error fetching collection.", e);
                    }
                });
    }

    private void addCategoryCheckbox(String categoryName) {
        MaterialCheckBox checkBox = new MaterialCheckBox(this);
        checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        checkBox.setText(categoryName);
        checkBox.setTextColor(getResources().getColor(R.color.white));
        checkBox.setTextSize(18);
        categoryContainer.addView(checkBox);
    }

    private List<String> getSelectedCategories() {
        List<String> selectedCategories = new ArrayList<>();
        int count = categoryContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = categoryContainer.getChildAt(i);
            if (view instanceof MaterialCheckBox) {
                MaterialCheckBox checkBox = (MaterialCheckBox) view;
                if (checkBox.isChecked()) {
                    selectedCategories.add(checkBox.getText().toString());
                }
            }
        }
        return selectedCategories;
    }

    private WorkingHours getWorkingHours(){
        WorkingHours workingHours = new WorkingHours();

        String monday = mondayOff.isChecked() ? "off" : this.monday.getText().toString() + " - " +  this.mondayEnd.getText().toString();
        String tuesday = tuesdayOff.isChecked() ? "off" : this.tuesday.getText().toString() + " - " + this.tuesdayEnd.getText().toString();
        String wendesday = wendesdayOff.isChecked() ? "off" : this.wendesday.getText().toString() + " - " + this.wendesdayEnd.getText().toString();
        String thursday = thursdayOff.isChecked() ? "off" : this.thursday.getText().toString() + " - " + this.thursdayEnd.getText().toString();
        String friday = fridayOff.isChecked() ? "off" : this.friday.getText().toString() + " - " + this.fridayEnd.getText().toString();
        String saturday = saturdayOff.isChecked() ? "off" : this.saturday.getText().toString() + " - " + this.saturdayEnd.getText().toString();
        String sunday = sundayOff.isChecked() ? "off" : this.sunday.getText().toString() + " - " + this.sundayEnd.getText().toString();

        workingHours.setWorkingHoursForDay("monday", monday);
        workingHours.setWorkingHoursForDay("tuesday", tuesday);
        workingHours.setWorkingHoursForDay("wednesday", wendesday);
        workingHours.setWorkingHoursForDay("thursday", thursday);
        workingHours.setWorkingHoursForDay("friday", friday);
        workingHours.setWorkingHoursForDay("saturday", saturday);
        workingHours.setWorkingHoursForDay("sunday", sunday);

        return workingHours;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}