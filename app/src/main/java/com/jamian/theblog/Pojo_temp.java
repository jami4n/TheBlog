package com.jamian.theblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jamian on 3/20/2017.
 */

public class Pojo_temp {
    @SerializedName("results_found")
    String results_found;

    @SerializedName("results_shown")
    String results_shown;

    public Pojo_temp(String results_found, String results_shown) {
        this.results_found = results_found;
        this.results_shown = results_shown;
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
}
