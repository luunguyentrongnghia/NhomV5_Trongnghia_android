package com.google.bookticket.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.bookticket.Model.Ticket;
import com.google.bookticket.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketsViewHolder> {
    List<Ticket> ticketList;
    Context context;
    FirebaseFirestore fdb;
    CollectionReference collec_tickets;


    public TicketAdapter(List<Ticket> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context = context;
    }

    @NonNull
    @Override
    public TicketsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lichsu, parent, false);

        return new TicketAdapter.TicketsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.TicketsViewHolder holder, int position) {
        fdb = FirebaseFirestore.getInstance();
        collec_tickets = fdb.collection("tickets");
        Ticket ticket = ticketList.get(position);
        if(ticket == null) {
            return;
        }

        holder.txtTenPhim.setText(ticket.getMovieName());
        holder.txtPhong.setText(ticket.getRoom());
        holder.txtTime.setText(ticket.getTimestart());
        holder.txtGheDat.setText(ticket.getSeat());
        holder.txtdate.setText(ticket.getDate());
        holder.btnxoalichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder dialogXoa = new AlertDialog.Builder(view.getContext());
                dialogXoa.setMessage("Bạn có muốn hủy vé này hay không?");

                dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fdb.collection("tickets")
                                .whereEqualTo("room",ticket.getRoom())
                                .whereEqualTo("seat",ticket.getSeat())
                                .whereEqualTo("movieName",ticket.getMovieName())
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful() && !task.getResult().isEmpty()){
                                            DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                                            String Seat=documentSnapshot.getId();
                                            fdb.collection("tickets")
                                                    .document(Seat)
                                                    .delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(view.getContext(), "Succesfull delete",Toast.LENGTH_LONG).show();
                                                            Toast.makeText(view.getContext(), "Please go out and comeback for loading",Toast.LENGTH_LONG).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(view.getContext(), "Some error occured",Toast.LENGTH_LONG).show();
                                                        }
                                                    });



                                        }else {
                                            Toast.makeText(view.getContext(),"Failed",Toast.LENGTH_LONG).show();
                                        }                            }

                                });


                    }
                });

                dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                dialogXoa.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(ticketList != null) {
            return ticketList.size();
        }
        return 0;
    }

    public class TicketsViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenPhim, txtPhong, txtTime, txtGheDat,txtdate;
        ImageView btnxoalichsu;
        Button refresh;
        public TicketsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdate=itemView.findViewById(R.id.txtdate);
            txtTenPhim = itemView.findViewById(R.id.txtTenPhim);
            txtPhong = itemView.findViewById(R.id.txtPhong);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtGheDat = itemView.findViewById(R.id.txtGheDat);
            btnxoalichsu = itemView.findViewById(R.id.btnxoalichsu);

        }
    }


}
