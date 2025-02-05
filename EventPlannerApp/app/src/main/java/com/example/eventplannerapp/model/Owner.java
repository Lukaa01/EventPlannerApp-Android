package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Owner implements Parcelable {

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
    @SerializedName("Categories")
    @Expose
    @Nullable
    private List<String> Categories;
    @SerializedName("EventTypes")
    @Expose
    @Nullable
    private List<String> EventTypes;
    @SerializedName("WorkingHours")
    @Expose
    @Nullable
    private WorkingHours WorkingHours;
    @SerializedName("CompanyEmail")
    @Expose
    private String CompanyEmail;
    @SerializedName("CompanyName")
    @Expose
    private String CompanyName;
    @SerializedName("CompanyAddress")
    @Expose
    private String CompanyAddress;
    @SerializedName("CompanyPhone")
    @Expose
    private String CompanyPhone;
    @SerializedName("CompanyDescription")
    @Expose
    private String CompanyDescription;
    @SerializedName("CompanyPhotos")
    @Expose
    private String CompanyPhotos;
    @SerializedName("Type")
    @Expose
    private OwnerType Type;
    @SerializedName("Timestamp")
    @Expose
    private long Timestamp;

    public enum OwnerType {
        COMPANY,
        AGENCY,
        STORE,
        ORGANIZATION
    }

    public Owner() {
    }

    protected Owner(Parcel in) {
        Id = in.readString();
        Email = in.readString();
        Password = in.readString();
        Firstname = in.readString();
        Lastname = in.readString();
        Photopath = in.readString();
        Address = in.readString();
        Phone = in.readString();
        Categories = in.createStringArrayList();
        EventTypes = in.createStringArrayList();
        WorkingHours = in.readParcelable(WorkingHours.class.getClassLoader());
        CompanyEmail = in.readString();
        CompanyName = in.readString();
        CompanyAddress = in.readString();
        CompanyPhone = in.readString();
        CompanyDescription = in.readString();
        CompanyPhotos = in.readString();
        Type = OwnerType.valueOf(in.readString());
        Timestamp = in.readLong();
    }

    public Owner(String id, String email, String password, String firstname, String lastname, @Nullable String photopath, String address, String phone, @Nullable List<String> categories, @Nullable List<String> eventTypes, @Nullable com.example.eventplannerapp.model.WorkingHours workingHours, String companyEmail, String companyName, String companyAddress, String companyPhone, String companyDescription, String companyPhotos, OwnerType type) {
        Id = id;
        Email = email;
        Password = password;
        Firstname = firstname;
        Lastname = lastname;
        Photopath = photopath;
        Address = address;
        Phone = phone;
        Categories = categories;
        EventTypes = eventTypes;
        WorkingHours = workingHours;
        CompanyEmail = companyEmail;
        CompanyName = companyName;
        CompanyAddress = companyAddress;
        CompanyPhone = companyPhone;
        CompanyDescription = companyDescription;
        CompanyPhotos = companyPhotos;
        Type = type;
        Timestamp = System.currentTimeMillis();
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
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
        parcel.writeStringList(Categories);
        parcel.writeStringList(EventTypes);
        parcel.writeParcelable(WorkingHours, i);
        parcel.writeString(CompanyEmail);
        parcel.writeString(CompanyName);
        parcel.writeString(CompanyAddress);
        parcel.writeString(CompanyPhone);
        parcel.writeString(CompanyDescription);
        parcel.writeString(CompanyPhotos);
        parcel.writeString(Type.name());
        parcel.writeLong(Timestamp);
    }

    // Getters and Setters

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

    @Nullable
    public List<String> getCategories() {
        return Categories;
    }

    public void setCategories(@Nullable List<String> categories) {
        Categories = categories;
    }

    @Nullable
    public List<String> getEventTypes() {
        return EventTypes;
    }

    public void setEventTypes(@Nullable List<String> eventTypes) {
        EventTypes = eventTypes;
    }

    @Nullable
    public com.example.eventplannerapp.model.WorkingHours getWorkingHours() {
        return WorkingHours;
    }

    public void setWorkingHours(@Nullable com.example.eventplannerapp.model.WorkingHours workingHours) {
        WorkingHours = workingHours;
    }

    public String getCompanyEmail() {
        return CompanyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        CompanyEmail = companyEmail;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyAddress() {
        return CompanyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        CompanyAddress = companyAddress;
    }

    public String getCompanyPhone() {
        return CompanyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        CompanyPhone = companyPhone;
    }

    public String getCompanyDescription() {
        return CompanyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        CompanyDescription = companyDescription;
    }

    public String getCompanyPhotos() {
        return CompanyPhotos;
    }

    public void setCompanyPhotos(String companyPhotos) {
        CompanyPhotos = companyPhotos;
    }

    public OwnerType getType() {
        return Type;
    }

    public void setType(OwnerType type) {
        Type = type;
    }

    public Long getTimestamp() {
        return Timestamp;
    }

    public Long setTimestamp(Long timestamp) {
        Timestamp = timestamp;
        return Timestamp;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "Id='" + Id + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", Firstname='" + Firstname + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", Photopath='" + Photopath + '\'' +
                ", Address='" + Address + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Categories=" + Categories +
                ", EventTypes=" + EventTypes +
                ", WorkingHours=" + WorkingHours +
                ", CompanyEmail='" + CompanyEmail + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                ", CompanyAddress='" + CompanyAddress + '\'' +
                ", CompanyPhone='" + CompanyPhone + '\'' +
                ", CompanyDescription='" + CompanyDescription + '\'' +
                ", CompanyPhotos='" + CompanyPhotos + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }
}
