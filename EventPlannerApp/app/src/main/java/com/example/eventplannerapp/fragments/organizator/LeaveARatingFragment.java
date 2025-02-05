package com.example.eventplannerapp.fragments.organizator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.Notification;
import com.example.eventplannerapp.model.Rating;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaveARatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaveARatingFragment extends Fragment {

    private EditText ratingValue;
    private EditText ratingComment;
    private EditText ratingUsername;
    private Button sendRatingButton;

    public LeaveARatingFragment() {
        // Required empty public constructor
    }

    public static LeaveARatingFragment newInstance(String param1, String param2) {
        LeaveARatingFragment fragment = new LeaveARatingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Handle fragment arguments here if needed
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leave_a_rating, container, false);

        // Initialize UI elements
        ratingValue = view.findViewById(R.id.rating_value);
        ratingComment = view.findViewById(R.id.rating_comment);
        ratingUsername = view.findViewById(R.id.rating_username);
        sendRatingButton = view.findViewById(R.id.send_rating_button);

        sendRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRating();
            }
        });

        return view;
    }

    private void sendRating() {
        String ratingStr = ratingValue.getText().toString();
        int ratingInt = Integer.parseInt(ratingStr);
        String comment = ratingComment.getText().toString();
        String username = ratingUsername.getText().toString();

        if (ratingStr.isEmpty() || comment.isEmpty() || username.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (ratingInt < 1 || ratingInt > 5) {
            Toast.makeText(getActivity(), "Rating can not be less than 1 or more than 5", Toast.LENGTH_SHORT).show();
            return;
        }

        int rating = Integer.parseInt(ratingStr);
        Date date = new Date();

        Rating newRating = new Rating();
        newRating.setRating(rating);
        newRating.setComment(comment);
        newRating.setDate(date);
        newRating.setUsername(username);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ratings")
                .add(newRating)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Rating submitted successfully", Toast.LENGTH_SHORT).show();
                        if (getFragmentManager() != null) {
                            getFragmentManager().popBackStack();
                        }

                        Notification notification = new Notification(
                                "Someone rated your company!",
                                "owner@gmail.com",
                                "RATING_SENT");
                        CloudStoreUtil.insertNotification(notification);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to submit rating", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
