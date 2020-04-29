package com.markovskij.lesson2.ui.cities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.markovskij.lesson2.R;
import com.markovskij.lesson2.ui.EventBus;
import com.markovskij.lesson2.ui.events.WeatherLoaderEvent;

import java.util.ArrayList;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {
    private ArrayList<Cities> list;

    public CityListAdapter(ArrayList<Cities> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.city.setText(list.get(position).getName());
        holder.city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getBus().post(new WeatherLoaderEvent(holder.city.getText().toString()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView city;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView){
            city = itemView.findViewById(R.id.cityListTextView);
        }
    }


}
