package com.markovskij.lesson2.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.markovskij.lesson2.R;
import com.markovskij.lesson2.ui.EventBus;
import com.markovskij.lesson2.ui.events.AddCitiesEvent;
import com.markovskij.lesson2.ui.events.WeatherLoaderEvent;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment {

    protected static TextView sunRise;
    protected static TextView sunSet;
    protected static TextView pressure;
    protected static TextView speedOfWind;
    protected static TextView city;
    protected static TextView temperature;
    private Button setting;
    protected static TextView date;
    protected static TextView weather;
    private final Handler handler = new Handler();
    protected static ImageView image;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        initView();
        if(savedInstanceState != null) {
            temperature.setText(savedInstanceState.getString("temp"));
            city.setText(savedInstanceState.getString("city"));
            date.setText(savedInstanceState.getString("date"));
            weather.setText(savedInstanceState.getString("weather"));
            sunRise.setText(savedInstanceState.getString("sunrise"));
            sunSet.setText(savedInstanceState.getString("sunset"));
            pressure.setText(savedInstanceState.getString("pressure"));
            speedOfWind.setText(savedInstanceState.getString("speed"));
        }
        initList();
        citySearchInWikipedia();
        searchCityWorld();
        setRetainInstance(true);
        return root;
    }

    private void initView(){
        sunRise = root.findViewById(R.id.textView_sunRise);
        sunSet = root.findViewById(R.id.textView_sunSet);
        pressure = root.findViewById(R.id.textView_pressure);
        speedOfWind = root.findViewById(R.id.textView_speedOfWind);
        city = root.findViewById(R.id.city);
        temperature = root.findViewById(R.id.temperature);
        setting = root.findViewById(R.id.settings);
        date = root.findViewById(R.id.full_date);
        weather = root.findViewById(R.id.weather);
        image = root.findViewById(R.id.imageView);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("city", city.getText().toString());
        outState.putString("date", date.getText().toString());
        outState.putString("temp", temperature.getText().toString());
        outState.putString("weather", weather.getText().toString());
        outState.putString("sunrise", sunRise.getText().toString());
        outState.putString("sunset", sunSet.getText().toString());
        outState.putString("pressure", pressure.getText().toString());
        outState.putString("speed", speedOfWind.getText().toString());
        super.onSaveInstanceState(outState);
    }

    private void citySearchInWikipedia(){
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://ru.wikipedia.org/wiki/" + city.getText().toString();
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void searchCityWorld(){
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
    }
    private void showInputDialog(){ //вывод диалогового окна
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle(R.string.search_city);
        builder.setIcon(R.drawable.globus);

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { //деяствие на кнопку
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateWeatherData(input.getText().toString());
                EventBus.getBus().post(new AddCitiesEvent(input.getText().toString()));
            }
        });
        builder.show();
    }

    @Subscribe
    public void onWeatherLoaderEvent(WeatherLoaderEvent event){
        updateWeatherData(event.city);
    }

    private void updateWeatherData(final String city) { //обновление данных и запрос на серевер
        new Thread() {
            @Override
            public void run() {
                final JSONObject jsonObject = WeatherDataLoader.getJSONData(city);//запрос на сервер
                if(jsonObject == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(R.string.Place_not_found);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            RenderWeather.renderWeather(jsonObject); //обработка запроса
                        }
                    });
                }
            }
        }.start();
    }


    private void initList() {
        RecyclerView rv_daily = root.findViewById(R.id.days);
        DailyCard dataList[] = new DailyCard[]{
                new DailyCard("26", ContextCompat.getDrawable(getContext(), R.drawable.sonne), "Tue"),
                new DailyCard("28", ContextCompat.getDrawable(getContext(), R.drawable.sonne), "Wed"),
                new DailyCard("27", ContextCompat.getDrawable(getContext(), R.drawable.sonne), "Thu"),
                new DailyCard("27", ContextCompat.getDrawable(getContext(), R.drawable.sonne), "Fri"),
                new DailyCard("27", ContextCompat.getDrawable(getContext(), R.drawable.sonne), "Sat"),
                new DailyCard("27", ContextCompat.getDrawable(getContext(), R.drawable.sonne), "Sun"),
                new DailyCard("25", ContextCompat.getDrawable(getContext(), R.drawable.sonne), "Mon"),
                new DailyCard("30", ContextCompat.getDrawable(getContext(), R.drawable.sonne), "Tue"),
                new DailyCard("32", ContextCompat.getDrawable(getContext(), R.drawable.sonne), "Wed"),
                new DailyCard("30", ContextCompat.getDrawable(getContext(), R.drawable.sonne), "Thu"),
                new DailyCard("27", ContextCompat.getDrawable(getContext(), R.drawable.sonne), "Fri"),
                new DailyCard("25", ContextCompat.getDrawable(getContext(), R.drawable.sonne), "Sat"),
                new DailyCard("27", ContextCompat.getDrawable(getContext(), R.drawable.sonne), "Sun"),
        };
        ArrayList<DailyCard> list = new ArrayList<>(dataList.length);
        list.addAll(Arrays.asList(dataList));
        ItemDailyAdapter adapter = new ItemDailyAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_daily.setLayoutManager(layoutManager);
        rv_daily.setAdapter(adapter);
    }


    @Override
    public void onStart() {
        EventBus.getBus().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getBus().unregister(this);
        super.onStop();
    }

}
