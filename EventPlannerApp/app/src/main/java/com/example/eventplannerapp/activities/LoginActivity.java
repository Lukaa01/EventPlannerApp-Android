package com.example.eventplannerapp.activities;

import static com.example.eventplannerapp.activities.ReportsActivity.SYNC_DATA;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.NotificationStatus;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.Notification;
import com.example.eventplannerapp.receivers.SyncReceiver;
import com.example.eventplannerapp.services.SyncService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginActivity extends AppCompatActivity {
    private static final String SYNC_CHANNEL_ID = "Sync channel";
    private final static String USER_BLOCKED = "USER_BLOCKED";
    private SyncReceiver syncReceiver;
    private static final String TAG = "LoginActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            setUpReceiver();
        //}
        createNotificationChannel("Sync channel", "Channel for sync notifications",
                SYNC_CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputLayout usernameInput = findViewById(R.id.usernameInput);
                TextInputLayout passwordInput = findViewById(R.id.passwordInput);

                String username = usernameInput.getEditText().getText().toString().trim();
                String password = passwordInput.getEditText().getText().toString().trim();

                if((username.equals("admin") && password.equals("admin") || (username.equals("a") && password.equals("a")))){
                    checkNotifications("admin@gmail.com").addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                startActivity(intent);
                            } else {
                                Log.d(TAG, "Failed to check notifications: ", task.getException());
                                Toast.makeText(LoginActivity.this, "Error checking notifications.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else if (username.equals("o") && password.equals("o")) {
                    checkNotifications("milibovan190d@gmail.com").addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {
                            if (task.isSuccessful()) {
                                boolean isUserBlocked = task.getResult();
                                if (!isUserBlocked) {
                                    Intent intent = new Intent(LoginActivity.this, OrganizatorHomeActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                Log.d(TAG, "Failed to check notifications: ", task.getException());
                                Toast.makeText(LoginActivity.this, "Error checking notifications.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else if (username.equals("owner") && password.equals("owner")) {
                    checkNotifications("owner@gmail.com").addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {
                            if (task.isSuccessful()) {
                                boolean isUserBlocked = task.getResult();
                                if (!isUserBlocked) {
                                    Intent intent = new Intent(LoginActivity.this, VlasnikHomeActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                Log.d(TAG, "Failed to check notifications: ", task.getException());
                                Toast.makeText(LoginActivity.this, "Error checking notifications.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else if (username.equals("staff") && password.equals("staff")) {
                    checkNotifications("staff@gmail.com").addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {
                            if (task.isSuccessful()) {
                                boolean isUserBlocked = task.getResult();
                                if (!isUserBlocked) {
                                    Intent intent = new Intent(LoginActivity.this, EmployeeHomeActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                Log.d(TAG, "Failed to check notifications: ", task.getException());
                                Toast.makeText(LoginActivity.this, "Error checking notifications.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    loginUser(username, password);
                }

                usernameInput.getEditText().setText("");
                passwordInput.getEditText().setText("");
            }
        });
    }

    //@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void setUpReceiver(){
        syncReceiver = new SyncReceiver();
        /*
         * Registrujemo nas BroadcastReceiver i dodajemo mu 'filter'.
         * Filter koristimo prilikom prispeca poruka. Jedan receiver
         * moze da reaguje na vise tipova poruka. One nam kazu
         * sta tacno treba da se desi kada poruka odredjenog tipa (filera)
         * stigne.
         * */
        IntentFilter filter = new IntentFilter();
        filter.addAction(SYNC_DATA);
        registerReceiver(syncReceiver, filter, RECEIVER_EXPORTED);
    }

    private void createNotificationChannel(CharSequence name, String description, String channel_id, int importance) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        NotificationChannel channel = new NotificationChannel(channel_id, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
    public Task<Boolean> checkNotifications(String email) {
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();
        AtomicBoolean isUserBlocked = new AtomicBoolean(false);

        CloudStoreUtil.getUnreadNotifications(email).thenAccept(notificationList -> {
            if (!notificationList.isEmpty()) {
                for (Notification n : notificationList) {
                    if (n.getNotificationType().equals(USER_BLOCKED)) {
                        Intent serviceIntent = new Intent(LoginActivity.this, SyncService.class);
                        serviceIntent.putExtra("key", n.getNotificationType());
                        serviceIntent.putExtra("description", n.getDescription());
                        startService(serviceIntent);
                        isUserBlocked.set(true);
                    } else {
                        Intent serviceIntent = new Intent(LoginActivity.this, SyncService.class);
                        serviceIntent.putExtra("key", n.getNotificationType());
                        serviceIntent.putExtra("description", n.getDescription());
                        startService(serviceIntent);
                    }
                    n.setNotificationStatus(NotificationStatus.READ);
                    CloudStoreUtil.updateNotificationStatus(n);
                }
            }
            taskCompletionSource.setResult(isUserBlocked.get());
        });

        return taskCompletionSource.getTask();
    }

    private void loginUser(String username, String password){
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null && user.isEmailVerified()) {
                                checkUserStatus(user);
                            } else {
                                Toast.makeText(LoginActivity.this, "Verify your email first.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong email or password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void checkUserStatus(FirebaseUser user) {
        db.collection("event_organizers").document(user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                boolean isActive = Boolean.TRUE.equals(document.getBoolean("active"));
                                if (isActive) {
                                    String token = generateToken(user);
                                    saveTokenToLocal(token);
                                    Intent intent = new Intent(LoginActivity.this, OrganizatorHomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Your account is inactive.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "User doesn't exist.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d(TAG, "Failed to check user status: ", task.getException());
                            Toast.makeText(LoginActivity.this, "Error, try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //TODO Create users table and get done
    private String generateToken(FirebaseUser user) {
        return user.getUid() ;
    }

    private void saveTokenToLocal(String token) {
        getSharedPreferences("app_prefs", MODE_PRIVATE)
                .edit()
                .putString("auth_token", token)
                .apply();


    }
}
