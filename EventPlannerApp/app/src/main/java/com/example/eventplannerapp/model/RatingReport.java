package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class RatingReport implements Parcelable {
    @DocumentId
    private String id;
    private String rating_id;
    private String username;
    private Date date;
    private String reason;
    private RatingReportStatus status;
    private String rejectReason = null;

    public RatingReport() { }

    public RatingReport(String id,
                        String rating_id,
                        String username,
                        Date date,
                        String reason,
                        RatingReportStatus status,
                        String rejectReason) {
        this.id = id;
        this.rating_id = rating_id;
        this.username = username;
        this.date = date;
        this.reason = reason;
        this.status = status;
        this.rejectReason = rejectReason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRating_id() {
        return rating_id;
    }

    public void setRating_id(String rating_id) {
        this.rating_id = rating_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RatingReportStatus getStatus() {
        return status;
    }

    public void setStatus(RatingReportStatus status) {
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(rating_id);
        dest.writeString(username);
        dest.writeString(date.toString());
        dest.writeString(reason);
        dest.writeString(status.name());
        dest.writeString(rejectReason);
    }
}


