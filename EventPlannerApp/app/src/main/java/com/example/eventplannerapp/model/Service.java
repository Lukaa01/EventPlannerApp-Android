package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Service implements Parcelable {
    private String id;
    private String title;
    private String description;
    private String category;
    private String subcategory;
    private int price;
    private int discount;
    private int discountedPrice;
    private double duration;
    private String location;
    private List<String> inCharge;
    private String specifics;
    private List<String> eventTypes;
    private List<String> images;
    private String reservationDeadline;
    private String cancellationDeadline;
    private boolean isAutomaticAccepted;
    private boolean visible;
    private boolean available;
    private boolean favourite;

    private double durationMin;
    private double durationMax;

    public Service() {

    }

    public Service(String id, String title, String description, String category, String subcategory, int price, int discount, int discountedPrice, double duration, String location, List<String> inCharge, String specifics, List<String> eventTypes, List<String> images, String reservationDeadline, String cancellationDeadline, boolean isAutomaticAccepted, boolean visible, boolean available, double durationMin, double durationMax,boolean favourite) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.subcategory = subcategory;
        this.price = price;
        this.discount = discount;
        this.duration = duration;
        this.location = location;
        this.inCharge = inCharge;
        this.specifics = specifics;
        this.eventTypes = eventTypes;
        this.discountedPrice = discountedPrice;
        this.images = images;
        this.reservationDeadline = reservationDeadline;
        this.cancellationDeadline = cancellationDeadline;
        this.isAutomaticAccepted = isAutomaticAccepted;
        this.visible = visible;
        this.available = available;
        this.favourite = favourite;
        this.durationMin = durationMin;
        this.durationMax = durationMax;
    }

    public Service(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        category = in.readString();
        subcategory = in.readString();
        price = in.readInt();
        discount = in.readInt();
        discountedPrice = in.readInt();
        location = in.readString();
        reservationDeadline = in.readString();
        cancellationDeadline = in.readString();
        eventTypes = in.createStringArrayList();
        images = in.createStringArrayList();
        inCharge = in.createStringArrayList();
        available = in.readInt() != 0;
        visible = in.readInt() != 0;
        specifics = in.readString();
        isAutomaticAccepted = in.readInt() != 0;
        favourite = in.readInt() != 0;
        durationMin = in.readDouble();
        durationMax = in.readDouble();
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

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getInCharge() {
        return inCharge;
    }

    public void setInCharge(List<String> inCharge) {
        this.inCharge = inCharge;
    }

    public String getSpecifics() {
        return specifics;
    }

    public void setSpecifics(String specifics) {
        this.specifics = specifics;
    }

    public List<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<String> eventTypes) {
        this.eventTypes = eventTypes;
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

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
    public double getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(double durationMin) {
        this.durationMin = durationMin;
    }

    public double getDurationMax() {
        return durationMax;
    }

    public void setDurationMax(double durationMax) {
        this.durationMax = durationMax;
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
        dest.writeString(category);
        dest.writeString(subcategory);
        dest.writeInt(price);
        dest.writeInt(discount);
        dest.writeInt(discountedPrice);
        dest.writeDouble(duration);
        dest.writeString(location);
        dest.writeString(reservationDeadline);
        dest.writeString(cancellationDeadline);
        dest.writeStringList(eventTypes);
        dest.writeStringList(images);
        dest.writeStringList(inCharge);
        dest.writeInt(available ? 1 : 0);
        dest.writeInt(visible ? 1 : 0);
        dest.writeString(specifics);
        dest.writeInt(isAutomaticAccepted ? 1 : 0);
        dest.writeInt(favourite ? 1 : 0);
        dest.writeDouble(durationMin);
        dest.writeDouble(durationMax);
    }

    public static final Parcelable.Creator<Service> CREATOR = new Parcelable.Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    @Override
    public String toString() {
        return "Service{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", discountedPrice=" + discountedPrice +
                ", duration=" + duration +
                ", location='" + location + '\'' +
                ", inCharge=" + inCharge +
                ", specifics='" + specifics + '\'' +
                ", eventTypes=" + eventTypes +
                ", images=" + images +
                ", reservationDeadline='" + reservationDeadline + '\'' +
                ", cancellationDeadline='" + cancellationDeadline + '\'' +
                ", isAutomaticAccepted=" + isAutomaticAccepted +
                ", visible=" + visible +
                ", available=" + available +
                ", durationMin=" + durationMin +
                ", durationMax=" + durationMax +
                ", favourite=" + favourite +
                '}';
    }
}
