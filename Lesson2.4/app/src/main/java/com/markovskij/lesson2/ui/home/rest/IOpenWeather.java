package com.markovskij.lesson2.ui.home.rest;

import com.markovskij.lesson2.ui.home.rest.entities.WeatherRequestModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeather {
    @GET ("data/2.5/forecast")
    Call<WeatherRequestModel> loadWeather (@Query("q") String city,
                                           @Query("units") String units,
                                           @Query("appid") String ApiKey);
}
