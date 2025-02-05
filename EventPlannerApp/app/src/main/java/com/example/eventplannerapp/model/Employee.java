package com.example.eventplannerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Employee implements Parcelable {
    private Long id;
    private String email;
    private String password;
    private String firstname;
    private String lastName;
    private String address;
    private String phoneNumber;
    private boolean accountActive;
    private WorkingHours availability;

    public Employee(Long id, String email, String password, String firstname, String lastName, String address, String phoneNumber, boolean accountActive, WorkingHours availability) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.accountActive = accountActive;
        this.availability = availability;
    }

    public Employee() {
    }

    protected Employee(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        email = in.readString();
        password = in.readString();
        firstname = in.readString();
        lastName = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
        accountActive = in.readByte() != 0;
        availability = in.readParcelable(WorkingHours.class.getClassLoader());
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAccountActive() {
        return accountActive;
    }

    public void setAccountActive(boolean accountActive) {
        this.accountActive = accountActive;
    }

    public WorkingHours getAvailability() {
        return availability;
    }

    public void setAvailability(WorkingHours availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return accountActive == employee.accountActive && Objects.equals(id, employee.id) && Objects.equals(email, employee.email) && Objects.equals(password, employee.password) && Objects.equals(firstname, employee.firstname) && Objects.equals(lastName, employee.lastName) && Objects.equals(address, employee.address) && Objects.equals(phoneNumber, employee.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, firstname, lastName, address, phoneNumber, accountActive);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(firstname + " " + lastName);
        dest.writeString(address);
        dest.writeString(phoneNumber);
        dest.writeParcelable(availability, flags);
    }
}
