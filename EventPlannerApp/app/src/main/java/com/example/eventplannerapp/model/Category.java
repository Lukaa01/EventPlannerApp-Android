package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category implements Parcelable {

    @SerializedName("Id")
    @Expose
    private String Id;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Description")
    @Expose
    private String Description;

    public Category() {
    }

    protected Category(Parcel in) {
        Name = in.readString();
        Description = in.readString();
    }

    public Category(String id, String name, String description) {
        Id = id;
        Name = name;
        Description = description;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(Description);
    }
}
