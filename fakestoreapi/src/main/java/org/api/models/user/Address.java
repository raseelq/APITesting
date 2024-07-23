package org.api.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Address {
    @JsonProperty("city")
    String city;
    @JsonProperty("street")

    String street;
    @JsonProperty("number")

    int number;

    @JsonProperty("zipcode")
    String zipcode;
    @JsonProperty("geolocation")
    Geolocation geolocation;
    @JsonProperty("phone")
    String phone;

    public Address(String city, String street, int number, String zipcode, Geolocation geolocation) {
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
        this.geolocation = geolocation;
    }

    public Address() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return number == address.number && Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(zipcode, address.zipcode) && Objects.equals(geolocation, address.geolocation) ;
    }

}
