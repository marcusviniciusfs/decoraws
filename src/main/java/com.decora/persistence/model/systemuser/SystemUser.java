package com.decora.persistence.model.systemuser;

import com.decora.persistence.model.AbstractEntity;
import com.decora.persistence.model.address.Address;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Embedded;

@Entity("systemuser")
public class SystemUser extends AbstractEntity {

    private String name;

    @Indexed(options = @IndexOptions(unique = true))
    private String email;

    private String password;

    private String phone;

    @Embedded
    private Address address;

    public final String getName() {
        return name;
    }

    public final void setName(final String theName) {
        name = theName;
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(final String theEmail) {
        email = theEmail;
    }

    public final String getPassword() {
        return password;
    }

    public final void setPassword(final String thePassword) {
        password = thePassword;
    }

    public final String getPhone() {
        return phone;
    }

    public final void setPhone(final String thePhone) {
        phone = thePhone;
    }

    public final Address getAddress() {
        return address;
    }

    public final void setAddress(final Address theAddress) {
        address = theAddress;
    }
}
