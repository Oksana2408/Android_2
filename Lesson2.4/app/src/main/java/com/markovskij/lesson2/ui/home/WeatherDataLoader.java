package com.markovskij.lesson2.ui.home;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherDataLoader {

    private static final String OPEN_WEATHER_API_KEY = "f74b06b338f2271c204e1f4d828683c3";
    private static final String OPEN_WEATHER_API_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";

    private static final String KEY = "x-api-key";



    static JSONObject getJSONData(String city) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_API_URL, city));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty(KEY, OPEN_WEATHER_API_KEY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tempVariable;

            while ((tempVariable = reader.readLine()) != null) {
                rawData.append(tempVariable).append("\n");
            }

            reader.close();

            JSONObject jsonObject = new JSONObject(rawData.toString());
            if(jsonObject.getInt("cod") != 200) {
                return null;
            } else {
                return jsonObject;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }
}
