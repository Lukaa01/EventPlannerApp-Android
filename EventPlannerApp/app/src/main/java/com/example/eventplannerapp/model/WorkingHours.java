package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class WorkingHours implements Parcelable {

    @SerializedName("hoursPerDay")
    @Expose
    private Map<String, String> hoursPerDay;

    public WorkingHours() {
        hoursPerDay = new HashMap<>();
        // Set default working hours
        hoursPerDay.put("monday", "closed");
        hoursPerDay.put("tuesday", "closed");
        hoursPerDay.put("wednesday", "closed");
        hoursPerDay.put("thursday", "closed");
        hoursPerDay.put("friday", "closed");
        hoursPerDay.put("saturday", "closed");
        hoursPerDay.put("sunday", "closed");
    }

    public WorkingHours(Map<String, String> hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    protected WorkingHours(Parcel in) {
        hoursPerDay = new HashMap<>();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            String value = in.readString();
            hoursPerDay.put(key, value);
        }
    }

    public static final Creator<WorkingHours> CREATOR = new Creator<WorkingHours>() {
        @Override
        public WorkingHours createFromParcel(Parcel in) {
            return new WorkingHours(in);
        }

        @Override
        public WorkingHours[] newArray(int size) {
            return new WorkingHours[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(hoursPerDay.size());
        for (Map.Entry<String, String> entry : hoursPerDay.entrySet()) {
            parcel.writeString(entry.getKey());
            parcel.writeString(entry.getValue());
        }
    }

    public String getWorkingHoursForDay(String day) {
        return hoursPerDay.get(day);
    }

    public void setWorkingHoursForDay(String day, String workingHours) {
        hoursPerDay.put(day, workingHours);
    }

    public Map<String, String> getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(Map<String, String> hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : hoursPerDay.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
