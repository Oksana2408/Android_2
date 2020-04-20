package com.markovskij.lesson2.ui.home;

import android.graphics.drawable.Drawable;

class DailyCard {
    private String itemTemperature;
    private Drawable itemImage;
    private String itemWeekDay;

    DailyCard(String itemTemperature, Drawable itemImage, String itemWeekDay) {
        this.itemTemperature = itemTemperature;
        this.itemImage = itemImage;
        this.itemWeekDay = itemWeekDay;
    }

    String getItemTemperature() {
        return itemTemperature;
    }

    Drawable getItemImage() {
        return itemImage;
    }

    String getItemWeekDay() {
        return itemWeekDay;
    }
}
