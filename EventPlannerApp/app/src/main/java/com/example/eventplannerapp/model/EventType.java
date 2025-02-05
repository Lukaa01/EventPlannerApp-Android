package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentId;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventType implements Parcelable {
    @DocumentId
    @SerializedName("Id")
    @Expose
    private String Id;
    @SerializedName("EventClass")
    @Expose
    private String EventClass;
    @SerializedName("Name")
    @Expose
    private String Name;

    protected EventType(Parcel in) {
        Id = in.readString();
        EventClass = in.readString();
        Name = in.readString();
    }

    public EventType() {
    }
    public EventType(String id, String name, String eventClass) {
        this.Id = id;
        this.Name = name;
        this.EventClass = eventClass;
    }
    public static final Creator<EventType> CREATOR = new Creator<EventType>() {
        @Override
        public EventType createFromParcel(Parcel in) {
            return new EventType(in);
        }

        @Override
        public EventType[] newArray(int size) {
            return new EventType[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEventClass() {
        return EventClass;
    }

    public void setEventClass(String eventClass) {
        EventClass = eventClass;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "EventType{" +
                "Id='" + Id + '\'' +
                ", EventClass='" + EventClass + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(EventClass);
        parcel.writeString(Name);
    }
}
