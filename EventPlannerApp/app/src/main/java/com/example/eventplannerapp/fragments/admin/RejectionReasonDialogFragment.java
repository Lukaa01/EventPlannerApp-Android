package com.example.eventplannerapp.fragments.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.EmailSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RejectionReasonDialogFragment extends DialogFragment {
    private RejectionReasonDialogListener mListener;
    private String ownerEmail;
    private String id;

    public static RejectionReasonDialogFragment newInstance(String ownerEmail, String id) {
        RejectionReasonDialogFragment fragment = new RejectionReasonDialogFragment();
        Bundle args = new Bundle();
        args.putString("owner_email", ownerEmail);
        args.putString("owner_id", id);
        fragment.setArguments(args);
        return fragment;
    }

    public interface RejectionReasonDialogListener {
        void onDialogPositiveClick(String rejectionReason);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_rejection_reason_dialog, null);
        final TextInputLayout rejectionReasonInputLayout = dialogView.findViewById(R.id.rejectInput);
        final TextInputEditText rejectionReasonInput = (TextInputEditText) rejectionReasonInputLayout.getEditText();

        ownerEmail = getArguments().getString("owner_email");
        id = getArguments().getString("owner_id");
        builder.setView(dialogView)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String rejectionReason = rejectionReasonInput.getText().toString();
                        Log.d("owner email", ownerEmail);
                        EmailSender.sendActivationEmail(ownerEmail, rejectionReason);
                        deleteRequest();
                        mListener.onDialogPositiveClick(rejectionReason);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (RejectionReasonDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement RejectionReasonDialogListener");
        }
    }

    private void deleteRequest() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("ownerRegistrationRequests").document(id);
        docRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Request rejected", Toast.LENGTH_SHORT).show(); // Added .show() here
                } else {
                    Toast.makeText(getContext(), "Failed to delete request", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
