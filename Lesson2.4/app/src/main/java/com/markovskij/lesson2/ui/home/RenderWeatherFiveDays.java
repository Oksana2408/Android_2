package com.markovskij.lesson2.ui.home;


import com.markovskij.lesson2.R;
import com.markovskij.lesson2.ui.home.rest.entities.WeatherRequestModel;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class RenderWeatherFiveDays {

    private static DailyCard [] dataList;

    static void renderWeatherFiveDays(WeatherRequestModel model){
//        setData(model.list[0].dt);
//        setTemperature(model.list[1].main.temp);
//        setIcon(model.list[3].weather[0].id, model.city.sunrise, model.city.sunset);
        //initList();

}
    private static void setTemperature(float temperature)  { //актуальная температура
        String temp = String.format(Locale.getDefault(), "%.0f", temperature);
        dataList[0].setItemTemperature(temp);
    }

    private static void setData(long dt) { //актуальная дата
        DateFormat dateTime = DateFormat.getDateTimeInstance();
        String updateOn = dateTime.format(new Date(dt * 1000));
        dataList[2].setItemWeekDay(updateOn);

    }

    private static void setIcon(int actualId, long sunRise, long sunSet){ //меняем иконки в соответствии с погодой (id)
        int id = actualId /100;

        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime >= sunRise && currentTime < sunSet){
                dataList[1].setItemImage(R.drawable.sonne);

            } else {
                dataList[1].setItemImage(R.drawable.nacht);

            }
        } else {
            switch (id){
                case 2:{
                    dataList[1].setItemImage(R.drawable.sturm);

                    break;
                }
                case 3: {
                    dataList[1].setItemImage(R.drawable.nieseln);

                }
                case 5: {
                    dataList[1].setItemImage(R.drawable.regen);

                    break;
                }
                case 6: {
                    dataList[1].setItemImage(R.drawable.schnee);

                    break;
                }
                case 7:{
                    dataList[1].setItemImage(R.drawable.nebel);

                    break;
                }
                case 8:{
                    dataList[1].setItemImage(R.drawable.wolke);

                    break;
                }
            }
        }
    }

//    static DailyCard[] initList(){
//        dataList = new DailyCard[]{
//                new DailyCard(dataList[0].getItemTemperature(), dataList[1].getItemImage(), dataList[2].getItemWeekDay())
//        };
//
//        return new DailyCard[0];
//    }
}
