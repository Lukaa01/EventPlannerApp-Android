package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.eventplannerapp.ReportStatus;
import com.google.firebase.firestore.DocumentId;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.net.ssl.SSLEngineResult;

public class Report implements Parcelable {
    @DocumentId
    private String id;
    private String idReportingUser;
    private String reportingName;
    private String typeReporting;
    private String idReportedUser;
    private String reportedName;
    private String typeReported;
    private String description;
    private Date reportTime;
    private ReportStatus status;

    public Report() {
    }

    public Report(String id, String idReportingUser,String reportingName, String typeReporting, String idReportedUser, String reportedName, String typeReported, String description, Date reportTime, ReportStatus status) {
        this.id = id;
        this.idReportingUser = idReportingUser;
        this.typeReporting = typeReporting;
        this.reportedName = reportedName;
        this.reportingName = reportingName;
        this.idReportedUser = idReportedUser;
        this.typeReported = typeReported;
        this.description = description;
        this.reportTime = reportTime;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdReportingUser() {
        return idReportingUser;
    }

    public void setIdReportingUser(String idReportingUser) {
        this.idReportingUser = idReportingUser;
    }

    public String getTypeReporting() {
        return typeReporting;
    }

    public void setTypeReporting(String typeReporting) {
        this.typeReporting = typeReporting;
    }

    public String getIdReportedUser() {
        return idReportedUser;
    }

    public void setIdReportedUser(String idReportedUser) {
        this.idReportedUser = idReportedUser;
    }

    public String getTypeReported() {
        return typeReported;
    }

    public void setTypeReported(String typeReported) {
        this.typeReported = typeReported;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public String getReportingName() {
        return reportingName;
    }

    public void setReportingName(String reportingName) {
        this.reportingName = reportingName;
    }

    public String getReportedName() {
        return reportedName;
    }

    public void setReportedName(String reportedName) {
        this.reportedName = reportedName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idReportingUser);
        dest.writeString(typeReporting);
        dest.writeString(idReportedUser);
        dest.writeString(typeReported);
        dest.writeString(description);
        dest.writeLong(reportTime.getTime());
        dest.writeString(status.name());
    }
    public Report(Parcel in){
        id = in.readString();
        idReportingUser = in.readString();
        typeReporting = in.readString();
        idReportedUser = in.readString();
        typeReported = in.readString();
        description = in.readString();
        reportTime = new Date(in.readLong());
        status = ReportStatus.valueOf(in.readString());
    }

    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };
}
