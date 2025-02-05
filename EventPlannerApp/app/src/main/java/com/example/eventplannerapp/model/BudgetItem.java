package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class BudgetItem implements Parcelable {
    private String id;
    private String subcategory;
    private double price;

    private String eventId;

    public BudgetItem(String id, String subcategory, double price, String eventId) {
        this.id = id;
        this.subcategory = subcategory;
        this.price = price;
        this.eventId = eventId;
    }

    public BudgetItem() {

    }

    protected BudgetItem(Parcel in) {
        id = in.readString();
        subcategory = in.readString();
        price = in.readDouble();
        eventId = in.readString();
    }

    // Setter methods
    public void setId(String id) {
        this.id = id;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public double getPrice() {
        return price;
    }
    public String getEventId() { return eventId; }
    public void setEventId(String budgetId) { this.eventId = budgetId; }

    public static final Creator<BudgetItem> CREATOR = new Creator<BudgetItem>() {
        @Override
        public BudgetItem createFromParcel(Parcel in) {
            return new BudgetItem(in);
        }

        @Override
        public BudgetItem[] newArray(int size) {
            return new BudgetItem[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(subcategory);
        dest.writeDouble(price);
        dest.writeString(eventId);
    }
}
