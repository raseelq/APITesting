package org.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Geolocation {
    String lat;
    @JsonProperty("long")
    String longi;

    public Geolocation() {
    }

    public Geolocation(String lat, String longi) {
        this.lat = lat;
        this.longi = longi;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geolocation that = (Geolocation) o;
        return Objects.equals(lat, that.lat) && Objects.equals(longi, that.longi);
    }

}
