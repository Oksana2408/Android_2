package com.markovskij.lesson2.ui.home;

import com.markovskij.lesson2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class RenderWeather {

    static void renderWeather(JSONObject jsonObject) { //парсим данные с сервера
        try {
            JSONObject details = jsonObject.getJSONArray("weather").getJSONObject(0);
            JSONObject main = jsonObject.getJSONObject("main");
            JSONObject sys = jsonObject.getJSONObject("sys");
            JSONObject wind = jsonObject.getJSONObject("wind");

            setCityName(jsonObject);
            setTemperature(main);
            setData(jsonObject);
            setIcon(details.getInt("id"), jsonObject.getJSONObject("sys").getLong("sunrise") * 1000,
                    jsonObject.getJSONObject("sys").getLong("sunset")*1000);
            setSunrise(sys);
            setSunset(sys);
            setSpeedOfWind(wind);
            setPressure(main);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setCityName(JSONObject jsonObject) throws JSONException { //имя города
        String cityText = jsonObject.getString("name").toUpperCase();
        HomeFragment.city.setText(cityText);

    }

    private static void setTemperature(JSONObject main) throws JSONException { //актуальная температура
        String temp = String.format(Locale.getDefault(), "%.0f", main.getDouble("temp")) + "°C";
        HomeFragment.temperature.setText(temp);

    }

    private static void setData(JSONObject jsonObject) throws JSONException { //актуальная дата
        DateFormat dateTime = DateFormat.getDateTimeInstance();
        String updateOn = dateTime.format((new Date(jsonObject.getLong("dt")*1000)));
        HomeFragment.date.setText(updateOn);

    }


    private static void setIcon(int actualId, long sunRise, long sunSet){ //меняем иконки в соответствии с погодой (id)
        int id = actualId /100;

        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime >= sunRise && currentTime < sunSet){
                HomeFragment.image.setImageResource(R.drawable.sonne);
                HomeFragment.weather.setText(R.string.weather);
            } else {
                HomeFragment.image.setImageResource(R.drawable.nacht);
                HomeFragment.weather.setText(R.string.clear);
            }
        } else {
            switch (id){
                case 2:{
                    HomeFragment.image.setImageResource(R.drawable.sturm);
                    HomeFragment.weather.setText(R.string.thunder);
                    break;
                }
                case 3: {
                    HomeFragment.image.setImageResource(R.drawable.nieseln);
                    HomeFragment.weather.setText(R.string.drizzle);
                }
                case 5: {
                    HomeFragment.image.setImageResource(R.drawable.regen);
                    HomeFragment.weather.setText(R.string.rainy);
                    break;
                }
                case 6: {
                    HomeFragment.image.setImageResource(R.drawable.schnee);
                    HomeFragment.weather.setText(R.string.snowy);
                    break;
                }
                case 7:{
                    HomeFragment.image.setImageResource(R.drawable.nebel);
                    HomeFragment.weather.setText(R.string.foggy);
                    break;
                }
                case 8:{
                    HomeFragment.image.setImageResource(R.drawable.wolke);
                    HomeFragment.weather.setText(R.string.cloud);
                    break;
                }
            }
        }
    }

    private static void setSunrise(JSONObject sys) throws JSONException { //получаем время восхода
        DateFormat dateFormat = DateFormat.getTimeInstance();
        String sr = "Восход" +" "+ dateFormat.format(new Date(sys.getLong("sunrise")*1000));
        HomeFragment.sunRise.setText(sr);
    }

    private static void setSunset(JSONObject sys) throws JSONException{ //получаем время заката
        DateFormat dateFormat = DateFormat.getTimeInstance();
        String ss = "Закат" + " "+ dateFormat.format(new Date(sys.getLong("sunset")*1000));
        HomeFragment.sunSet.setText(ss);
    }

    private static void setSpeedOfWind(JSONObject wind)throws JSONException{ //получаем скорость ветра
        String speed = "Скорость ветра" +" "+ wind.getString("speed") + " m/h";
        HomeFragment.speedOfWind.setText(speed);

    }

    private static void setPressure(JSONObject main) throws JSONException{ //получаем показатели давления
        String pressure2 = "Давление" +" " + main.getString("pressure") + " hPa";
        HomeFragment.pressure.setText(pressure2);
    }

}
