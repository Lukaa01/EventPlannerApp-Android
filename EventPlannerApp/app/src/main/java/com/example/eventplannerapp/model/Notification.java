package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.eventplannerapp.NotificationStatus;
import com.google.firebase.firestore.DocumentId;

public class Notification implements Parcelable {
    @DocumentId
    private String id;
    private String description;
    private String email;
    private String notificationType;
    private NotificationStatus notificationStatus;

    public Notification() {
    }

    public boolean isRead() {
        return notificationStatus == NotificationStatus.READ;
    }

    public Notification(String description, String email, String type) {
        this(description, email, type, NotificationStatus.UNREAD);
    }

    public Notification(String description, String email, String type, NotificationStatus status) {
        this.description = description;
        this.email = email;
        this.notificationType = type;
        this.notificationStatus = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Notification(Parcel in){
        id = in.readString();
        email = in.readString();
        description = in.readString();
        notificationType = in.readString();
        notificationStatus = NotificationStatus.valueOf(in.readString());
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(description);
        dest.writeString(notificationType);
        dest.writeString(notificationStatus.name());
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
}
