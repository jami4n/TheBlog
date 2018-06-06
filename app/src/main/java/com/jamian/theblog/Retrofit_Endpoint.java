package com.jamian.theblog;

import com.jamian.theblog.ZomatoData.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by jamian on 3/20/2017.
 */

public interface Retrofit_Endpoint {

    @Headers({
            "user-key: 9900a9720d31dfd5fdb4352700c6ff45"
    })
    @GET("search")
    Call<Data> getRestaurantsBySearch(@Query("entity_id") String entity_id, @Query("entity_type") String entity_type, @Query("q") String query);


}
