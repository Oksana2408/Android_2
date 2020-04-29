package com.markovskij.lesson2.ui.home.rest.entities;

import com.google.gson.annotations.SerializedName;

public class ListModel {
    @SerializedName("dt") public long dt;
    @SerializedName("main") public MainModel main;
    @SerializedName("weather") public WeatherModel [] weather;
    @SerializedName("clouds") public CloudsModel clouds;
    @SerializedName("wind") public WindModel wind;
    @SerializedName("sys") public SysModel sys;
    @SerializedName("dt_txt") public String dtText;
}
