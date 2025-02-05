package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class Rating implements Parcelable
{
    @DocumentId
    private String Id;
    private int Rating;
    private String comment;
    private Date date;
    private String username;

    public Rating(String id, int rating, String comment, Date date, String username) {
        Id = id;
        Rating = rating;
        this.comment = comment;
        this.date = date;
        this.username = username;
    }

    public Rating() {
    }

    protected Rating(Parcel in) {
        Id = in.readString();
        Rating = in.readInt();
        comment = in.readString();
        username = in.readString();
    }

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };

    @Override
    public String toString() {
        return "Rating{" +
                "Id=" + Id +
                ", Rating=" + Rating +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                ", username='" + username + '\'' +
                '}';
    }

    public void setId(String id) {
        Id = id;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return Id;
    }

    public int getRating() {
        return Rating;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeInt(Rating);
        dest.writeString(comment);
        dest.writeString(username);
    }
}
