package com.markovskij.lesson2.ui.home.rest.entities;

import com.google.gson.annotations.SerializedName;

public class CityModel {

    @SerializedName("id") public long id;
    @SerializedName("coord") public CoordModel coord;
    @SerializedName("country") public String country;
    @SerializedName("population") public long population;
    @SerializedName("timezone") public int timeZone;
    @SerializedName("sunrise") public long sunrise;
    @SerializedName("sunset") public long sunset;
}
