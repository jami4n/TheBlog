package com.jamian.theblog.ZomatoData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jamian on 3/20/2017.
 */

public class RestaurantObj {
    @SerializedName("restaurant")
    Restaurant restaurant;

    public RestaurantObj(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
