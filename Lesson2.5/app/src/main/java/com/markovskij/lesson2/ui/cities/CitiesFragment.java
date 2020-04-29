package com.markovskij.lesson2.ui.cities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.markovskij.lesson2.R;
import com.markovskij.lesson2.ui.EventBus;
import com.markovskij.lesson2.ui.database.NotesTable;
import com.markovskij.lesson2.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class CitiesFragment extends Fragment {

    private CitiesViewModel slideshowViewModel;
    private RecyclerView listCity;
    private String text;
    View root;
    SQLiteDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(CitiesViewModel.class);
        root = inflater.inflate(R.layout.fragment_city_list, container, false);
        initRecyclerView();
        return root;
    }

    private void initRecyclerView(){
        NotesTable.getAllInfo(database);

        }
//        ArrayList<Cities> cities = new ArrayList<>(list.length);
//        cities.addAll(Arrays.asList(list));
//        CityListAdapter adapter = new CityListAdapter(cities);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        listCity.setAdapter(adapter);
//        listCity.setLayoutManager(layoutManager);
//    }

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
