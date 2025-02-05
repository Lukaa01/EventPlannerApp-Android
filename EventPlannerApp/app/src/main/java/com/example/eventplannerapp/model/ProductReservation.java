package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProductReservation implements Parcelable {
    private String Id;
    private Product Product;
    private ReservationStatus ReservationStatus;
    private String Email;

    public ProductReservation(com.example.eventplannerapp.model.Product product, ReservationStatus reservationStatus, String id, String email) {
        this.Product = product;
        this.ReservationStatus = reservationStatus;
        this.Id = id;
        this.Email = email;
    }

    public ProductReservation() {
    }

    public com.example.eventplannerapp.model.Product getProduct() {
        return Product;
    }

    public void setProduct(com.example.eventplannerapp.model.Product product) {
        this.Product = product;
    }

    public ReservationStatus getReservationStatus() {
        return ReservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.ReservationStatus = reservationStatus;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "ProductReservation{" +
                "product=" + Product +
                ", reservationStatus=" + ReservationStatus +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(Product, i);
        parcel.writeString(Id);
        parcel.writeString(ReservationStatus.name());
        parcel.writeString(Email);
    }

    protected ProductReservation(Parcel in){
        Id = in.readString();
        Product = in.readParcelable(Product.class.getClassLoader());
        ReservationStatus = com.example.eventplannerapp.model.ReservationStatus.valueOf(in.readString());
        Email = in.readString();
    }
}
