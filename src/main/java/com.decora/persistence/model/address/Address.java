package com.decora.persistence.model.address;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Address {

    private String number;
    private String street;
    private String town;
    private String state;
    private String postcode;

    public final String getNumber() {
        return number;
    }

    public final void setNumber(final String theNumber) {
        this.number = theNumber;
    }

    public final String getStreet() {
        return street;
    }

    public final void setStreet(final String theStreet) {
        this.street = theStreet;
    }

    public final String getTown() {
        return town;
    }

    public final void setTown(final String theTown) {
        this.town = theTown;
    }

    public final String getState() {
        return state;
    }

    public final void setState(final String theState) {
        this.state = theState;
    }

    public final String getPostcode() {
        return postcode;
    }

    public final void setPostcode(final String thePostcode) {
        this.postcode = thePostcode;
    }
}
