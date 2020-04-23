package com.markovskij.lesson2.ui.home.rest.entities;

import com.google.gson.annotations.SerializedName;

public class WeatherRequestModel {
    @SerializedName("cod") public String code;
    @SerializedName("message") public int message;
    @SerializedName("cnt") public  int cnt;
    @SerializedName("list") public ListModel [] list;
    @SerializedName("city") public CityModel city;

}
