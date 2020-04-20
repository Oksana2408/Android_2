package com.markovskij.lesson2.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.markovskij.lesson2.R;

import java.util.ArrayList;

public class ItemDailyAdapter extends RecyclerView.Adapter<ItemDailyAdapter.ViewHolder> {
    private ArrayList<DailyCard> dailyCardList;

    public ItemDailyAdapter(ArrayList<DailyCard> dailyCardList) {
        this.dailyCardList = dailyCardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.temperature.setText(dailyCardList.get(position).getItemTemperature());
        holder.image.setImageDrawable(dailyCardList.get(position).getItemImage());
        holder.weekDays.setText(dailyCardList.get(position).getItemWeekDay());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Погода на " + holder.weekDays.getText() + " ", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dailyCardList == null ? 0: dailyCardList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView temperature;
        ImageView image;
        TextView weekDays;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView){
            temperature = itemView.findViewById(R.id.itemTemperature);
            image = itemView.findViewById(R.id.itemImage);
            weekDays = itemView.findViewById(R.id.itemWeekDay);
        }
    }
}
