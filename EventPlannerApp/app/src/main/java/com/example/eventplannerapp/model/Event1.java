package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Event1 implements Parcelable {
    private String eventId;
    private String name;
    private String description;
    private String location;
    private String eventType;
    /*
    private String category;
    private String subcategory;*/
    private String date;
    private int numberOfGuests;

    public Event1() {

    }

    // Constructor
    public Event1(String id, String name, String description, String location, String eventType,
                /* String category, String subcategory,*/ String date, int numberOfGuests) {
        this.eventId = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.eventType = eventType;
        /*
        this.category = category;
        this.subcategory = subcategory;*/
        this.date = date;
        this.numberOfGuests = numberOfGuests;
    }
    public Event1(String name, String description, String location, String eventType,
            /* String category, String subcategory,*/ String date, int numberOfGuests) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.eventType = eventType;
        /*
        this.category = category;
        this.subcategory = subcategory;*/
        this.date = date;
        this.numberOfGuests = numberOfGuests;
    }

    // Getters and Setters

    // Parcelable implementation

    protected Event1(Parcel in) {
        /*
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        location = in.readString();
        eventType = in.readString();
        /*
        category = in.readString();
        subcategory = in.readString();
        date = in.readString();
        numberOfGuests = in.readInt();*/
    }

    public static final Creator<Event1> CREATOR = new Creator<Event1>() {
        @Override
        public Event1 createFromParcel(Parcel in) {
            return new Event1(in);
        }

        @Override
        public Event1[] newArray(int size) {
            return new Event1[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(eventId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeString(eventType);
        /*
        dest.writeString(category);
        dest.writeString(subcategory);*/
        dest.writeString(date);
        dest.writeInt(numberOfGuests);
    }
    public String getId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getEventType() {
        return eventType;
    }
/*
    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }*/

    public String getDate() {
        return date;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

}
