package com.example.eventplannerapp.fragments.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.eventplannerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OwnerDetailedFragment extends DialogFragment {

    private static final String ARG_ITEM_ID = "item_id";
    private String itemId;
    private String email;

    public static OwnerDetailedFragment newInstance(String itemId) {
        OwnerDetailedFragment fragment = new OwnerDetailedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_detailed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            itemId = getArguments().getString(ARG_ITEM_ID);
        }

        // Initialize your views here
        MaterialTextView ownerEmail = view.findViewById(R.id.owner_email);
        MaterialTextView ownerFirstname = view.findViewById(R.id.owner_firstname);
        MaterialTextView ownerLastname = view.findViewById(R.id.owner_lastname);
        MaterialTextView ownerAddress = view.findViewById(R.id.owner_address);
        MaterialTextView ownerPhone = view.findViewById(R.id.owner_phone);
        MaterialTextView ownerCategories = view.findViewById(R.id.owner_categories);
        MaterialTextView ownerEventTypes = view.findViewById(R.id.owner_event_types);
        MaterialTextView companyWorkingHours = view.findViewById(R.id.company_working_hours);
        MaterialTextView companyEmail = view.findViewById(R.id.company_email);
        MaterialTextView companyName = view.findViewById(R.id.company_name);
        MaterialTextView companyAddress = view.findViewById(R.id.company_address);
        MaterialTextView companyPhone = view.findViewById(R.id.company_phone);
        MaterialTextView companyDescription = view.findViewById(R.id.company_description);
        MaterialTextView companyType = view.findViewById(R.id.company_type);
        MaterialTextView timestamp = view.findViewById(R.id.timestamp);

        Log.d("item", itemId);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("ownerRegistrationRequests").document(itemId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        email = document.getString("email");
                        ownerEmail.setText("Owner email: \t" + document.getString("email"));
                        ownerFirstname.setText("Owner firstname: \t" + document.getString("firstname"));
                        ownerLastname.setText("Owner lastname: \t" + document.getString("lastname"));
                        ownerAddress.setText("Owner address: \t" + document.getString("address"));
                        ownerPhone.setText("Owner phone: \t" + document.getString("phone"));

                        List<String> categories = (List<String>) document.get("categories");
                        if (categories != null) {
                            ownerCategories.setText("Owner categories: \t" + TextUtils.join(", ", categories));
                        } else {
                            ownerCategories.setText("Owner categories: \tN/A");
                        }

                        List<String> eventTypes = (List<String>) document.get("eventTypes");
                        if (eventTypes != null) {
                            ownerEventTypes.setText("Owner events: \t" + TextUtils.join(", ", eventTypes));
                        } else {
                            ownerEventTypes.setText("Owner events: \tN/A");
                        }

                        Map<String, Object> workingHours = (Map<String, Object>) document.get("workingHours");
                        if (workingHours != null) {
                            StringBuilder workingHoursText = new StringBuilder();
                            for (Map.Entry<String, Object> entry : workingHours.entrySet()) {
                                workingHoursText.append(entry.getKey()).append(": ").append(entry.getValue().toString()).append("\n");
                            }
                            companyWorkingHours.setText("Company working hours: \t" + workingHoursText.toString());
                        } else {
                            companyWorkingHours.setText("Company working hours: \tN/A");
                        }

                        companyEmail.setText("Company email: \t" + document.getString("companyEmail"));
                        companyName.setText("Company name: \t" + document.getString("companyName"));
                        companyAddress.setText("Company address: \t" + document.getString("companyAddress"));
                        companyPhone.setText("Company phone: \t" + document.getString("companyPhone"));
                        companyDescription.setText("Company description: \t" + document.getString("companyDescription"));
                        companyType.setText("Company type: \t" + document.getString("type"));

                        Long timestampMillis = document.getLong("timestamp");
                        if (timestampMillis != null) {
                            Date date = new Date(timestampMillis);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            String formattedDate = sdf.format(date);
                            timestamp.setText("Requested: \t" + formattedDate);
                        } else {
                            timestamp.setText("Requested: \tN/A");
                        }

                    } else {
                        Log.d("base", "No such document");
                    }
                } else {
                    Log.d("base", "get failed with ", task.getException());
                }
            }
        });

        MaterialButton rejectButton = view.findViewById(R.id.cancel_button);
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the rejection reason dialog
                RejectionReasonDialogFragment dialogFragment = RejectionReasonDialogFragment.newInstance(email, itemId);
                dialogFragment.show(getParentFragmentManager(), "RejectionReasonDialogFragment");
            }
        });

        MaterialButton confirmButton = view.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOwnerAndCompany();
            }
        });


        MaterialButton closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> dismiss());
    }
    private void createOwnerAndCompany() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("ownerRegistrationRequests").document(itemId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(document.getString("email"), document.getString("password"))
                        .addOnCompleteListener(taskAuth -> {
                            if (taskAuth.isSuccessful()) {
                                FirebaseUser user = taskAuth.getResult().getUser();
                                String ownerId = user.getUid();

                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                DocumentReference ownerRef = db.collection("owners").document(ownerId);

                                Map<String, Object> ownerData = new HashMap<>();
                                ownerData.put("id", user.getUid());
                                ownerData.put("email", document.getString("email"));
                                ownerData.put("password", document.getString("password"));
                                ownerData.put("firstname", document.getString("firstname"));
                                ownerData.put("lastname", document.getString("lastname"));
                                ownerData.put("address", document.getString("address"));
                                ownerData.put("phone", document.getString("phone"));
                                ownerData.put("photopath", document.getString("photopath"));
                                ownerData.put("companyEmail", document.getString("companyEmail"));

                                ownerRef.set(ownerData)
                                        .addOnSuccessListener(aVoid -> {
                                            DocumentReference companyRef = db.collection("companies").document();

                                            // Add company fields to the document
                                            Map<String, Object> companyData = new HashMap<>();
                                            companyData.put("id", user.getUid());
                                            companyData.put("email", document.getString("companyEmail"));
                                            companyData.put("description", document.getString("companyDescription"));
                                            companyData.put("name", document.getString("companyName"));
                                            companyData.put("address", document.getString("companyAddress"));
                                            companyData.put("phone", document.getString("companyPhone"));
                                            companyData.put("photopath", document.getString("companyPhotos"));
                                            companyData.put("ownerEmail", document.getString("email"));
                                            companyData.put("categories", document.get("categories"));
                                            companyData.put("eventTypes", document.get("eventTypes"));
                                            companyData.put("type", document.getString("type"));
                                            companyData.put("workingHours", document.get("workingHours"));
                                            // Add other company fields as needed
                                            companyRef.set(companyData)
                                                    .addOnSuccessListener(aVoid1 -> {
                                                        sendVerificationEmail(user);

                                                        deleteRequest();
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e("Company Creation", "Error creating company", e);
                                                        Toast.makeText(getContext(), "Error creating company", Toast.LENGTH_SHORT).show();
                                                    });
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.e("Owner Creation", "Error creating owner", e);
                                            Toast.makeText(getContext(), "Error creating owner", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Log.e("User Creation", "Error creating user", task.getException());
                                Toast.makeText(getContext(), "Error creating user", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Log.d("base", "No such document");
                    }
                } else {
                    Log.d("base", "get failed with ", task.getException());
                }
            }
        });
    }

    private void sendVerificationEmail(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("Email Verification", "Email sent.");
                        Toast.makeText(getContext(), "Verification email sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Email Verification", "Failed to send verification email", task.getException());
                        Toast.makeText(getContext(), "Failed to send verification email", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteRequest() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("ownerRegistrationRequests").document(itemId);
        docRef.delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "User verified rejected", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Request Deletion", "Error deleting request", task.getException());
                        Toast.makeText(getContext(), "Error deleting request", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
