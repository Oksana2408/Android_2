package com.markovskij.lesson2.ui.home.rest.entities;

import com.google.gson.annotations.SerializedName;

public class MainModel {

    @SerializedName("temp") public float temp;
    @SerializedName("feels_like") public float feelsLike;
    @SerializedName("temp_min") public float tempMin;
    @SerializedName("temp_max") public float tempMax;
    @SerializedName("pressure") public int pressure;
    @SerializedName("sea_level") public int seaLevel;
    @SerializedName("grnd level") public int grndLevel;
    @SerializedName("humidity") public int humidity;
    @SerializedName("temp_kf") public float tempKf;
}
