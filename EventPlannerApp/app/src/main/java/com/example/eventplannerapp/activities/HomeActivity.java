package com.example.eventplannerapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventplannerapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            if (uri != null && "activate".equals(uri.getHost())) {
                String activationCode = uri.getQueryParameter("code");
                String collection = uri.getQueryParameter("collection");
                db.collection(collection).document(activationCode).update("IsActive", true)
                        .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "User successfully changed"))
                        .addOnFailureListener(e -> Log.w("REZ_DB", "Error updating user. ", e));
            }
        }

        Button loginButton =findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button signinButton = findViewById(R.id.signinButton);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}