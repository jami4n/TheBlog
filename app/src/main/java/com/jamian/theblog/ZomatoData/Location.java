package com.jamian.theblog.ZomatoData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jamian on 3/20/2017.
 */

public class Location {

    @SerializedName("address")
    String address;

    @SerializedName("city")
    String city;

    @SerializedName("locality")
    String locality;

    public Location(String address, String city, String locality) {
        this.address = address;
        this.city = city;
        this.locality = locality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
}
