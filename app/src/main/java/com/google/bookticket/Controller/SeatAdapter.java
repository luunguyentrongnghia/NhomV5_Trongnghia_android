package com.google.bookticket.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.bookticket.ChooseSeatActivity;
import com.google.bookticket.Model.Seat;
import com.google.bookticket.R;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    List<Seat> seatList;
    ChooseSeatActivity chooseSeatActivity;

    public SeatAdapter(List<Seat> seatList, ChooseSeatActivity chooseSeatActivity) {
        this.seatList = seatList;
        this.chooseSeatActivity = chooseSeatActivity;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(chooseSeatActivity).inflate(R.layout.item_seat, parent, false);

        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Seat seat = seatList.get(position);
        if(seat == null) {
            return;
        }
        holder.item_seat_seat_txt.setText(seat.getSeat_txt());
        if(seat.getSeat_position() == 1) {
            holder.item_seat_seat_position.setEnabled(false);
            holder.item_seat_seat_position.setButtonDrawable(R.drawable.seat_red);
            holder.item_seat_seat_txt.setBackgroundResource(R.drawable.seat_red);

        } else {
            holder.item_seat_seat_position.setEnabled(true);
        }

        holder.item_seat_seat_position.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true) {
                    chooseSeatActivity.choseSeat.add(seat.getSeat_txt());
                    chooseSeatActivity.chose_seat_seatbook.setText(chooseSeatActivity.choseSeat.toString());
                    chooseSeatActivity.chose_seat_countseat.setText(chooseSeatActivity.choseSeat.size() + "");
                    chooseSeatActivity.chose_seat_amount.setText(chooseSeatActivity.choseSeat.size()*55000 + "");
                } else {
                    for(int index = 0; index <= chooseSeatActivity.choseSeat.size(); index++) {
                        if(chooseSeatActivity.choseSeat.get(index).equalsIgnoreCase(seat.getSeat_txt())) {
                            chooseSeatActivity.choseSeat.remove(index);
                            chooseSeatActivity.chose_seat_seatbook.setText(chooseSeatActivity.choseSeat.toString());
                            chooseSeatActivity.chose_seat_countseat.setText(chooseSeatActivity.choseSeat.size() + "");
                            chooseSeatActivity.chose_seat_amount.setText(chooseSeatActivity.choseSeat.size()*55000 + "");
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(seatList != null) {
            return seatList.size();
        }
        return 0;
    }

    public class SeatViewHolder extends RecyclerView.ViewHolder {
        TextView item_seat_seat_txt , chose_seat_seatbook, chose_seat_countseat  ;
        CheckBox item_seat_seat_position;
        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            item_seat_seat_txt = itemView.findViewById(R.id.item_seat_seat_txt);
            item_seat_seat_position = itemView.findViewById(R.id.item_seat_seat_position);

        }
    }
}
