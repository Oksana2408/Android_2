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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.markovskij.lesson2.R;
import com.markovskij.lesson2.ui.EventBus;
import com.markovskij.lesson2.ui.events.AddCitiesEvent;
import com.markovskij.lesson2.ui.events.WeatherLoaderEvent;
import com.markovskij.lesson2.ui.home.rest.OpenWeatherRepo;
import com.markovskij.lesson2.ui.home.rest.entities.WeatherRequestModel;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    static TextView sunRise;
    static TextView sunSet;
    static TextView pressure;
    static TextView speedOfWind;
    static TextView city;
    static TextView temperature;
    private Button setting;
    static TextView date;
    static TextView weather;
    private final Handler handler = new Handler();
    static ImageView image;
    View root;
    static  ImageView photo;
    private HomeViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
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
//        initList();
        citySearchInWikipedia();
        searchCityWorld();
        setRetainInstance(true);
        loadPictureInternet();
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
//                updateWeatherFiveDays(input.getText().toString());
                EventBus.getBus().post(new AddCitiesEvent(input.getText().toString()));
            }
        });
        builder.show();
    }

    @Subscribe
    public void onWeatherLoaderEvent(WeatherLoaderEvent event){
        updateWeatherData(event.city);
        updateWeatherFiveDays(event.city);
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

    private void updateWeatherFiveDays(final String city){
         OpenWeatherRepo.getSingleTon().getAPI().loadWeather(city,
                "metric", "f74b06b338f2271c204e1f4d828683c3")
                .enqueue(new Callback<WeatherRequestModel>() {
                    @Override
                    public void onResponse(Call<WeatherRequestModel> call, Response<WeatherRequestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            RenderWeatherFiveDays.renderWeatherFiveDays(response.body());
                        } else {
                            //Похоже, код у нас не в диапазоне [200..300) и случилась ошибка
                            //обрабатываем ее
                            if(response.code() == 500) {
                                //ой, случился Internal Server Error. Решаем проблему
                            } else if(response.code() == 401) {
                                //не авторизованы, что-то с этим делаем.
                                //например, открываем страницу с логинкой
                            }// и так далее
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequestModel> call, Throwable t) {

                    }
                });
    }


//    private void initList() {
//        RecyclerView rv_daily = root.findViewById(R.id.days);
//        DailyCard dataList[] = RenderWeatherFiveDays.initList();
//        ArrayList<DailyCard> list = new ArrayList<>(dataList.length);
//        list.addAll(Arrays.asList(dataList));
//        ItemDailyAdapter adapter = new ItemDailyAdapter(list);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        rv_daily.setLayoutManager(layoutManager);
//        rv_daily.setAdapter(adapter);
//    }

    private void loadPictureInternet(){
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo = root.findViewById(R.id.internetPicture);
                Picasso.get()
                        .load("https://images.pexels.com/photos/1237119/pexels-photo-1237119.jpeg")
                        .placeholder(R.drawable.sonne)
                        .into(photo);
            }
        });
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
