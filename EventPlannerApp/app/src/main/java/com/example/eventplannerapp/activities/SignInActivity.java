package com.example.eventplannerapp.activities;

import static android.service.controls.ControlsProviderService.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.EventOrganizer;
import com.example.eventplannerapp.model.Owner;
import com.example.eventplannerapp.model.WorkingHours;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.List;
import java.util.UUID;

public class SignInActivity extends AppCompatActivity {

    private final String organizersCollection = "event_organizers";
    private final String tempCollection = "temp_users";
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final int REQUEST_STORAGE_PERMISSION = 102;
    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private TextInputEditText confirmedPasswordInput;
    private TextInputEditText firstnameInput;
    private TextInputEditText lastnameInput;
    private TextInputEditText addressInput;
    private TextInputEditText phoneInput;
    private RadioButton ownerRadioButton;
    private Uri photoUri;
    private RadioButton organizerRadioButton;
    private ImageView imageView;
    private FloatingActionButton floatingActionButton;
    private static final int REQUEST_PERMISSIONS = 200;
    private boolean isPermissions = true;
    private String Id = UUID.randomUUID().toString();

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Handler handler = new Handler();
    private final String[] permissions = {
            Manifest.permission.ACCESS_MEDIA_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        imageView = findViewById(R.id.imageView);
        floatingActionButton = findViewById(R.id.selectImageButton);

        // Initialize views
        TextInputLayout usernameLayout = findViewById(R.id.usernameInput);
        TextInputLayout passwordLayout = findViewById(R.id.passwordInput);
        TextInputLayout confirmedPasswordLayout = findViewById(R.id.confirmedPasswordInput);
        TextInputLayout firstnameLayout = findViewById(R.id.nameInput);
        TextInputLayout lastnameLayout = findViewById(R.id.lastnameInput);
        TextInputLayout addressLayout = findViewById(R.id.addressInput);
        TextInputLayout phoneLayout = findViewById(R.id.phoneInput);
        usernameInput = (TextInputEditText) usernameLayout.getEditText();
        passwordInput = (TextInputEditText) passwordLayout.getEditText();
        confirmedPasswordInput = (TextInputEditText) confirmedPasswordLayout.getEditText();
        firstnameInput = (TextInputEditText) firstnameLayout.getEditText();
        lastnameInput = (TextInputEditText) lastnameLayout.getEditText();
        addressInput = (TextInputEditText) addressLayout.getEditText();
        phoneInput = (TextInputEditText) phoneLayout.getEditText();
        ownerRadioButton = findViewById(R.id.ownerRadioButton);
        organizerRadioButton = findViewById(R.id.organizerRadioButton);
        Button signinButton = findViewById(R.id.signinButton);

        RadioGroup radioGroup = findViewById(R.id.radioButtonsLayout);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.ownerRadioButton) {
                    signinButton.setText("Next");
                }
            }
        });
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = validateInputs();
                if (result) {
                    if (ownerRadioButton.isChecked()) {
                        String photoUriString = (photoUri != null) ? photoUri.toString() : null;
                        Gson gson = new Gson();
                        Intent intent = new Intent(SignInActivity.this, OwnerRegistartionActivity.class);
                        Owner owner = getOwnerFields(Id,
                                usernameInput.getText().toString().trim(),
                                passwordInput.getText().toString().trim(),
                                firstnameInput.getText().toString().trim(),
                                lastnameInput.getText().toString().trim(),
                                photoUriString,
                                addressInput.getText().toString().trim(),
                                phoneInput.getText().toString().trim(), null, null, null,
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                Owner.OwnerType.AGENCY);

                        String ownerJson = gson.toJson(owner);
                        intent.putExtra("owner", ownerJson);
                        startActivity(intent);
                    } else {
                        registerEventOrganizer();
                    }
                }
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRequestPermission();
            }
        });

        startVerificationCheck();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE) {
            photoUri = data.getData();
            imageView.setImageURI(photoUri);
        }
    }

    private boolean validateInputs() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmedPassword = confirmedPasswordInput.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showToast("Please fill in all fields");
            return false;
        }

        if (!ownerRadioButton.isChecked() && !organizerRadioButton.isChecked()) {
            showToast("Please select a role");
            return false;
        }

        if (!password.equals(confirmedPassword)) {
            showToast("Passwords are not the same");
            return false;
        }

        return true;
    }

    private void onRequestPermission() {
        ActivityCompat.requestPermissions(SignInActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        ActivityCompat.requestPermissions(SignInActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
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
        ImagePicker.with(SignInActivity.this)
                .crop()                //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void registerEventOrganizer() {
        mAuth.createUserWithEmailAndPassword(usernameInput.getText().toString().trim(), passwordInput.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                sendVerificationEmail(user);
                            }
                        } else {
                            showToast("Authentication failed.");
                        }
                    }
                });
    }

    private void sendVerificationEmail(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showToast("Verification email sent to " + user.getEmail());
                            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                            saveUserToTempFirestore(user);
                            startActivity(intent);
                        } else {
                            showToast("Failed to send verification email.");
                        }
                    }
                });
    }

    private void saveUserToTempFirestore(FirebaseUser user) {
        String photoUriString = (photoUri != null) ? photoUri.toString() : null;

        EventOrganizer organizer = EventOrganizer.create(
                Id,
                usernameInput.getText().toString().trim(),
                passwordInput.getText().toString().trim(),
                firstnameInput.getText().toString().trim(),
                lastnameInput.getText().toString().trim(),
                photoUriString,
                addressInput.getText().toString().trim(),
                phoneInput.getText().toString().trim()
        );

        db.collection(tempCollection).document(user.getUid())
                .set(organizer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User added to temp collection for verification.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding user to temp collection.", e);
                    }
                });
    }

    private void moveUserToMainCollection(String userId) {
        db.collection(tempCollection).document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                db.collection(organizersCollection).document(userId)
                                        .set(document.getData())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "User moved to main collection.");
                                                deleteUserFromTempCollection(userId);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error moving user to main collection.", e);
                                            }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Failed to get user data from temp collection.", task.getException());
                        }
                    }
                });
    }

    private void deleteUserFromTempCollection(String userId) {
        db.collection(tempCollection).document(userId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User deleted from temp collection.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting user from temp collection.", e);
                    }
                });
    }

    private void deleteUserFromAuth(String userId) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User account deleted.");
                            } else {
                                Log.w(TAG, "Error deleting user account.", task.getException());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error moving user to main collection.", e);
                    }
                });
        }
    }

    private void checkForInactiveUsers() {
        long currentTime = System.currentTimeMillis();
        long thresholdTime = currentTime - (24 * 60 * 60 * 1000); // 24 hours in milliseconds

        db.collection(tempCollection)
                .whereLessThan("timestamp", thresholdTime)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String userId = document.getId();
                                deleteUserFromTempCollection(userId);
                                deleteUserFromAuth(userId);
                            }
                        } else {
                            Log.d(TAG, "Error checking for inactive users.", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error moving user to main collection.", e);
                    }
                });
    }

    private void startVerificationCheck() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                if (user.isEmailVerified()) {
                                    moveUserToMainCollection(user.getUid());
                                }
                            }
                        }
                    });
                }
                checkForInactiveUsers();
                handler.postDelayed(this, 10000);
            }
        }, 10000);
    }


    private Owner getOwnerFields(String id, String email, String password, String firstname, String lastname, @Nullable String photopath, String address, String phone, List<String> categories, List<String> eventTypes, WorkingHours workingHours, String companyEmail, String companyName, String companyAddress, String companyPhone, String companyDescription, String companyPhotos, Owner.OwnerType type) {
        return new Owner(id, email, password, firstname, lastname, photopath, address, phone, categories, eventTypes, workingHours, companyEmail, companyName, companyAddress, companyPhone, companyDescription, companyPhotos, type);
    }

    private void registerOwner() {
        // Implement owner registration logic
    }
}
