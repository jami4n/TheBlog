package com.jamian.theblog;

import android.util.Log;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jamian on 3/20/2017.
 */

public class Retro_Client {

    public static String BASE_URL = "https://developers.zomato.com/api/v2.1/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }
        return retrofit;
    }


}
