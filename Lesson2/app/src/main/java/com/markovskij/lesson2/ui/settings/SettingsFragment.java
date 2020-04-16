package com.markovskij.lesson2.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.markovskij.lesson2.R;
import com.markovskij.lesson2.ui.EventBus;


public class SettingsFragment extends Fragment {

    private SettingsViewModel galleryViewModel;

    final String TAG = "lifecycle";
    private CheckBox sunRise;
    private CheckBox sunSet;
    private CheckBox pressure;
    private CheckBox speedOfWind;
    private Button add;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        root = inflater.inflate(R.layout.fragment_settings, container, false);
        initView();
        if(savedInstanceState != null){
            sunRise.setChecked(savedInstanceState.getBoolean("sunrise", false));
            sunSet.setChecked(savedInstanceState.getBoolean("sunset", false));
            pressure.setChecked(savedInstanceState.getBoolean("pressure", false));
            speedOfWind.setChecked(savedInstanceState.getBoolean("speed_of_wind", false));
        }
        return root;
    }

    protected void initView(){
        sunRise = root.findViewById(R.id.sunrise);
        sunSet = root.findViewById(R.id.sunset);
        pressure = root.findViewById(R.id.pressure);
        speedOfWind = root.findViewById(R.id.speed_of_wind);
        add = root.findViewById(R.id.button_add);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("sunrise", sunRise.isChecked());
        outState.putBoolean("sunset", sunSet.isChecked());
        outState.putBoolean("pressure", pressure.isChecked());
        outState.putBoolean("speed_of_wind", speedOfWind.isChecked());
        super.onSaveInstanceState(outState);
    }

    public void addBtnSelected() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
