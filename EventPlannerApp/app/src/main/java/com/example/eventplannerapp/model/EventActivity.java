package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentId;

import java.time.LocalTime;

public class EventActivity implements Parcelable {
    private String id;
    private String activityName;
    private String activityDescription;
    private String startTime;
    private String endTime;
    private String location;

    // Constructors
    public EventActivity(String id, String activityName, String activityDescription, String startTime, String endTime, String location) {
        this.id = id;
        this.activityName = activityName;
        this.activityDescription = activityDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public EventActivity(String activityName, String activityDescription, String startTime, String endTime, String location) {
        this.activityName = activityName;
        this.activityDescription = activityDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public EventActivity(Parcel in) {
        id = in.readString();
        activityName = in.readString();
        activityDescription = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        location = in.readString();
    }

    public EventActivity() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "EventActivity{" +
                "id='" + id + '\'' +
                ", activityName='" + activityName + '\'' +
                ", activityDescription='" + activityDescription + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(activityName);
        dest.writeString(activityDescription);
        dest.writeSerializable(startTime);
        dest.writeSerializable(endTime);
        dest.writeString(location);
    }

    public static final Creator<EventActivity> CREATOR = new Creator<EventActivity>() {
        @Override
        public EventActivity createFromParcel(Parcel in) {
            return new EventActivity(in);
        }

        @Override
        public EventActivity[] newArray(int size) {
            return new EventActivity[size];
        }
    };
}
