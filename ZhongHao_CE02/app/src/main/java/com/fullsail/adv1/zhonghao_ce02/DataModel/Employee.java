// Hao Zhong
// AD1 - 202111
// Employee.java
package com.fullsail.adv1.zhonghao_ce02.DataModel;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable {
    // Stored Fields
    private final String firstName;
    private final String lastName;
    private final int id;
    private final Date hireDate;
    private final String status;

    // Constructor
    public Employee(String _firstName, String _lastName, int _id, Date _hireDate, String _status) {
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.id = _id;
        this.hireDate = _hireDate;
        this.status = _status;
    }

    public String getFName() {
        return firstName;
    }
    public String getLName() { return lastName; }
    public String getStatus() {
        return status;
    }
    public int getId() {
        return id;
    }
    public Date getHDate() { return hireDate; }

    @NonNull
    @Override
    public String toString() {
        return firstName + " " + lastName + "\n" + id;
    }

    public String getDetails() {
        return "Name:\n" + firstName + lastName + "\n\nID:\n" + id + "\n\nDate Hired:\n" + hireDate + "\n\nEmployment Status:\n" + status;
    }
}
