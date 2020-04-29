package com.markovskij.lesson2.ui.home.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OpenWeatherRepo {
    private static OpenWeatherRepo singleTon = null;
    private IOpenWeather API;

    private OpenWeatherRepo(){
        API = createAdapter();
    }

    public static OpenWeatherRepo getSingleTon() {
        if(singleTon == null){
            singleTon = new OpenWeatherRepo();
        }
        return singleTon;
    }

    public IOpenWeather getAPI() {
        return API;
    }

    private IOpenWeather createAdapter() {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return adapter.create(IOpenWeather.class);
    }
}
