package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentId;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventOrganizer implements Parcelable, Serializable {
    @DocumentId
    @SerializedName("Id")
    @Expose
    private String Id;
    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("Firstname")
    @Expose
    private String Firstname;
    @SerializedName("Lastname")
    @Expose
    private String Lastname;
    @Nullable
    @SerializedName("Photopath")
    @Expose
    private String Photopath;
    @SerializedName("Address")
    @Expose
    private String Address;

    @SerializedName("Phone")
    @Expose
    private String Phone;
    @SerializedName("IsActive")
    @Expose
    private Boolean IsActive;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    @Nullable
    public String getPhotopath() {
        return Photopath;
    }

    public void setPhotopath(@Nullable String photopath) {
        Photopath = photopath;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    @NonNull
    @Override
    public String toString() {
        return "EventOrganizer{" +
                "Id=" + Id +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", Firstname='" + Firstname + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", Photopath='" + Photopath + '\'' +
                ", Address='" + Address + '\'' +
                ", Phone='" + Phone + '\'' +
                ", IsActive=" + IsActive +
                '}';
    }

    public static EventOrganizer create(String id, String email, String password, String firstname, String lastname, String photoPath, String address, String phone) {
        EventOrganizer organizer = new EventOrganizer();
        organizer.setId(id);
        organizer.setEmail(email);
        organizer.setPassword(password);
        organizer.setFirstname(firstname);
        organizer.setLastname(lastname);
        organizer.setPhotopath(photoPath);
        organizer.setAddress(address);
        organizer.setPhone(phone);
        organizer.setActive(false);
        return organizer;
    }

    public EventOrganizer() {
    }
    public EventOrganizer(Parcel in) {
        Id = in.readString();
        Email = in.readString();
        Password = in.readString();
        Firstname = in.readString();
        Lastname = in.readString();
        Photopath = in.readString();
        Address = in.readString();
        Phone = in.readString();
        IsActive = in.readInt() != 0;
    }

    public static final Creator<EventOrganizer> CREATOR = new Creator<EventOrganizer>() {
        @Override
        public EventOrganizer createFromParcel(Parcel in) {
            return new EventOrganizer(in);
        }

        @Override
        public EventOrganizer[] newArray(int size) {
            return new EventOrganizer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(Email);
        parcel.writeString(Password);
        parcel.writeString(Firstname);
        parcel.writeString(Lastname);
        parcel.writeString(Photopath);
        parcel.writeString(Address);
        parcel.writeString(Phone);
        parcel.writeInt(IsActive ? 1 : 0);
    }
}
