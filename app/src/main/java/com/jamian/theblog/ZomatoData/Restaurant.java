package com.jamian.theblog.ZomatoData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jamian on 3/20/2017.
 */

public class Restaurant {

    @SerializedName("name")
    String name;

    @SerializedName("location")
    Location location;

    @SerializedName("thumb")
    String thumb;

    public Restaurant(String name, Location location, String thumb) {
        this.name = name;
        this.location = location;
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
