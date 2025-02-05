package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.eventplannerapp.EventType;

import java.util.List;

public class Package implements Parcelable {
    private String id;
    private String title;
    private String description;
    private String category;
    private int price;
    private double discount;
    private List<Product> products;
    private List<Service> services;
    private List<String> images;
    private String reservationDeadline;
    private String cancellationDeadline;
    private boolean isAutomaticAccepted;
    private boolean visible;
    private boolean available;

    public Package() {

    }

    public Package(String id, String title, String description, String category, int price, double discount, List<Product> products, List<Service> services, List<String> images, String reservationDeadline, String cancellationDeadline, boolean isAutomaticAccepted, boolean visible, boolean available) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.discount = discount;
        this.products = products;
        this.services = services;
        this.images = images;
        this.reservationDeadline = reservationDeadline;
        this.cancellationDeadline = cancellationDeadline;
        this.isAutomaticAccepted = isAutomaticAccepted;
        this.visible = visible;
        this.available = available;
    }

    public Package(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        price = in.readInt();

        //dodaj sva polja...
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }


    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getReservationDeadline() {
        return reservationDeadline;
    }

    public void setReservationDeadline(String reservationDeadline) {
        this.reservationDeadline = reservationDeadline;
    }

    public String getCancellationDeadline() {
        return cancellationDeadline;
    }

    public void setCancellationDeadline(String cancellationDeadline) {
        this.cancellationDeadline = cancellationDeadline;
    }

    public boolean isAutomaticAccepted() {
        return isAutomaticAccepted;
    }

    public void setAutomaticAccepted(boolean automaticAccepted) {
        isAutomaticAccepted = automaticAccepted;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(price);
        //dodaj sva polja...
    }

    public static final Parcelable.Creator<Package> CREATOR = new Parcelable.Creator<Package>() {
        @Override
        public Package createFromParcel(Parcel in) {
            return new Package(in);
        }

        @Override
        public Package[] newArray(int size) {
            return new Package[size];
        }
    };
}
