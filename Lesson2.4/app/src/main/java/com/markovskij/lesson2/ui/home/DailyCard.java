package com.markovskij.lesson2.ui.home;

import android.graphics.drawable.Drawable;


class DailyCard {
    private String itemTemperature;
    private int itemImage;
    private String itemWeekDay;

    DailyCard(String itemTemperature, int itemImage, String itemWeekDay) {
        this.itemTemperature = itemTemperature;
        this.itemImage = itemImage;
        this.itemWeekDay = itemWeekDay;
    }

    String getItemTemperature() {
        return itemTemperature;
    }

    int getItemImage() {
        return itemImage;
    }

    public String getItemWeekDay() {
        return itemWeekDay;
    }

    public void setItemTemperature(String itemTemperature) {
        this.itemTemperature = itemTemperature;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    public void setItemWeekDay(String itemWeekDay) {
        this.itemWeekDay = itemWeekDay;
    }
}
