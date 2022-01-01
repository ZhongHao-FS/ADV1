// Hao Zhong
// AD1 - 202111
// Contact.java
package com.fullsail.adv1.zhonghao_04;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Contact implements Serializable {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String priNumber;
    private final String secNumber;
    private final byte[] blob;

    public Contact(String _id, String _firstName, String _lastName, @Nullable String _middleName,
                   String _priNumber, @Nullable String _secNumber, @Nullable byte[] _blob) {
        this.id = _id;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.middleName = _middleName;
        this.priNumber = _priNumber;
        this.secNumber = _secNumber;
        this.blob = _blob;
    }

    public String getId() { return id; }

    public String getFirstLastName() {
        return firstName + " " + lastName;
    }

    public String getFullName() {
        if (middleName == null) {
            return getFirstLastName();
        } else {
            return firstName + " " + middleName + " " + lastName;
        }
    }

    public String getPriNumber() {
        return priNumber;
    }

    @Nullable
    public String getSecNumber() {
        return secNumber;
    }

    @Nullable
    public byte[] getBlob() {
        return blob;
    }
}
