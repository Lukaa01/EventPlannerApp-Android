package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ServiceReservation implements Parcelable {
    private String id;
    private Service service;
    private ReservationStatus reservationStatus;
    private String employeeName;
    private String date;
    private String startTime;
    private String endTime;
    private String email;

    public ServiceReservation(Service service, ReservationStatus reservationStatus, String id, String employeeName, String date, String startTime, String endTime, String email) {
        this.service = service;
        this.reservationStatus = reservationStatus;
        this.id = id;
        this.employeeName = employeeName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.email = email;
    }

    public ServiceReservation() {
    }

    public static final Creator<ServiceReservation> CREATOR = new Creator<ServiceReservation>() {
        @Override
        public ServiceReservation createFromParcel(Parcel in) {
            return new ServiceReservation(in);
        }

        @Override
        public ServiceReservation[] newArray(int size) {
            return new ServiceReservation[size];
        }
    };

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getString() {
        return date;
    }

    public void setString(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ServiceReservation{" +
                "service=" + service +
                ", reservationStatus=" + reservationStatus +
                ", date=" + date +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(service, i);
        parcel.writeString(id);
        parcel.writeString(reservationStatus.name());
        parcel.writeString(date);
        parcel.writeString(startTime);
        parcel.writeString(endTime);
        parcel.writeString(employeeName);
        parcel.writeString(email);
    }

    protected ServiceReservation(Parcel in){
        service = in.readParcelable(Service.class.getClassLoader());
        id = in.readString();
        reservationStatus = ReservationStatus.valueOf(in.readString());
        date = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        employeeName = in.readString();
        email = in.readString();
    }
}
