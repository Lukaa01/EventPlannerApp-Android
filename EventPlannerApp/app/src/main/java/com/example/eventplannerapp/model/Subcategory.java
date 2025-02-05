package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentId;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subcategory implements Parcelable {
    @DocumentId
    @SerializedName("Id")
    @Expose
    private String Id;
    @SerializedName("Category")
    @Expose
    private Category Category;
    @SerializedName("Type")
    @Expose
    private String Type;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Description")
    @Expose
    private String Description;

    protected Subcategory(Parcel in) {
        Id = in.readString();
        Category = in.readParcelable(com.example.eventplannerapp.model.Category.class.getClassLoader());
        Type = in.readString();
        Name = in.readString();
        Description = in.readString();
    }

    public Subcategory(String id, com.example.eventplannerapp.model.Category category, String type, String name, String description) {
        Id = id;
        Category = category;
        Type = type;
        Name = name;
        Description = description;
    }

    public static final Creator<Subcategory> CREATOR = new Creator<Subcategory>() {
        @Override
        public Subcategory createFromParcel(Parcel in) {
            return new Subcategory(in);
        }

        @Override
        public Subcategory[] newArray(int size) {
            return new Subcategory[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public com.example.eventplannerapp.model.Category getCategory() {
        return Category;
    }

    public void setCategory(com.example.eventplannerapp.model.Category category) {
        Category = category;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Subcategory{" +
                "Id='" + Id + '\'' +
                ", Category=" + Category +
                ", Type='" + Type + '\'' +
                ", Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeParcelable(Category, i);
        parcel.writeString(Type);
        parcel.writeString(Name);
        parcel.writeString(Description);
    }
}
