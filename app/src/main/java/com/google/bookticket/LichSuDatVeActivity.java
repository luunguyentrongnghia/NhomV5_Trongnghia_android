package com.google.bookticket;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.bookticket.Controller.TicketAdapter;
import com.google.bookticket.Model.Ticket;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LichSuDatVeActivity extends AppCompatActivity {

    List<Ticket> ticketList;
    TicketAdapter ticketAdapter;
    RecyclerView rvLichSu;
    String userID;
    Button refresh;
    FirebaseFirestore fdb;
    CollectionReference collec_tickets;
    ImageView movielichsu_btnback;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_dat_ve);

        movielichsu_btnback = (ImageView) findViewById(R.id.btnback_movielichsu);
        fdb = FirebaseFirestore.getInstance();
        collec_tickets = fdb.collection("tickets");

        ticketList = new ArrayList<>();
        rvLichSu = findViewById(R.id.rvLichSu);
        ticketAdapter = new TicketAdapter(ticketList, LichSuDatVeActivity.this);

        rvLichSu.setLayoutManager(new LinearLayoutManager(LichSuDatVeActivity.this));
        rvLichSu.setAdapter(ticketAdapter);
        bundle = getIntent().getBundleExtra("userID");
        userID=bundle.getString("userID");

        collec_tickets
                .whereEqualTo("userID",userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                                Ticket ticket = documentSnapshot.toObject(Ticket.class);
                                ticket.setMovieID(documentSnapshot.getId());
                                ticketList.add(ticket);
                            }
                            ticketAdapter = new TicketAdapter(ticketList, LichSuDatVeActivity.this);
                            ticketAdapter.notifyDataSetChanged();
                            rvLichSu.setAdapter(ticketAdapter);
                        }
                    }
                });

        movielichsu_btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

}