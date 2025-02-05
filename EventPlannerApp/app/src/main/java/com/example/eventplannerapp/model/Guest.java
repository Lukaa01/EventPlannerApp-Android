package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;

public class Guest implements Parcelable {
    @DocumentId
    private String id;
    private String guestName;
    private String ageGroup;
    private boolean invited;
    private boolean accepted;
    private String specialRequests;

    // Constructors
    public Guest(String id, String guestName, String ageGroup, boolean invited, boolean accepted, String specialRequests) {
        this.id = id;
        this.guestName = guestName;
        this.ageGroup = ageGroup;
        this.invited = invited;
        this.accepted = accepted;
        this.specialRequests = specialRequests;
    }

    public Guest(String guestName, String ageGroup, boolean invited, boolean accepted, String specialRequests) {
        this.guestName = guestName;
        this.ageGroup = ageGroup;
        this.invited = invited;
        this.accepted = accepted;
        this.specialRequests = specialRequests;
    }

    public Guest(Parcel in) {
        id = in.readString();
        guestName = in.readString();
        ageGroup = in.readString();
        invited = in.readByte() != 0;
        accepted = in.readByte() != 0;
        specialRequests = in.readString();
    }

    public Guest() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public boolean isInvited() {
        return invited;
    }

    public void setInvited(boolean invited) {
        this.invited = invited;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "id='" + id + '\'' +
                ", guestName='" + guestName + '\'' +
                ", ageGroup='" + ageGroup + '\'' +
                ", invited=" + invited +
                ", accepted=" + accepted +
                ", specialRequests='" + specialRequests + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(guestName);
        dest.writeString(ageGroup);
        dest.writeByte((byte) (invited ? 1 : 0));
        dest.writeByte((byte) (accepted ? 1 : 0));
        dest.writeString(specialRequests);
    }

    public static final Creator<Guest> CREATOR = new Creator<Guest>() {
        @Override
        public Guest createFromParcel(Parcel in) {
            return new Guest(in);
        }

        @Override
        public Guest[] newArray(int size) {
            return new Guest[size];
        }
    };
}
