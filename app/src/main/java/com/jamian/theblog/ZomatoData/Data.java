package com.jamian.theblog.ZomatoData;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamian on 3/20/2017.
 */

public class Data {

    @SerializedName(("results_found"))
    String results_found;

    @SerializedName("results_shown")
    String results_shown;

    @SerializedName("restaurants")
    List<RestaurantObj> restaurants = new ArrayList<>();


    public Data(String results_found, String results_shown, List<RestaurantObj> restaurants) {
        this.results_found = results_found;
        this.results_shown = results_shown;
        this.restaurants = restaurants;
    }

    public String getResults_found() {
        return results_found;
    }

    public void setResults_found(String results_found) {
        this.results_found = results_found;
    }

    public String getResults_shown() {
        return results_shown;
    }

    public void setResults_shown(String results_shown) {
        this.results_shown = results_shown;
    }

    public List<RestaurantObj> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<RestaurantObj> restaurants) {
        this.restaurants = restaurants;
    }
}
