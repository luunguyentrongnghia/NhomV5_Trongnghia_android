package com.google.bookticket.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.bookticket.ChooseSeatActivity;
import com.google.bookticket.ChooseTimeActivity;
import com.google.bookticket.MoviesFragment;
import com.google.bookticket.R;
import com.google.bookticket.TimeStart;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {
    List<TimeStart> list;
    ChooseTimeActivity chooseTimeActivity;
    MoviesFragment moviesFragment;
    public TimeAdapter(List<TimeStart> list, ChooseTimeActivity chooseTimeActivity) {
        this.list = list;
        this.chooseTimeActivity = chooseTimeActivity;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(chooseTimeActivity).inflate(R.layout.item_timestart, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        TimeStart t = list.get(position);
        if(t == null) {
            return;
        }

        holder.timestart_start.setText(t.getTime());
        holder.timestart_room.setText(t.getRoom());
        holder.datestart.setText(t.getDate());
        holder.timestart_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTimeActivity.ChooseSeat(t.getTime(),t.getRoom(),chooseTimeActivity.movieID,chooseTimeActivity.movieName,t.getDate());

            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder {
        TextView timestart_start, timestart_room,datestart;
        Button timestart_choose;
        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            timestart_start = itemView.findViewById(R.id.timestart_start);
            timestart_room = itemView.findViewById(R.id.timestart_room);
            timestart_choose = itemView.findViewById(R.id.timestart_choose);
            datestart=itemView.findViewById(R.id.date_Start);
        }
    }
}
