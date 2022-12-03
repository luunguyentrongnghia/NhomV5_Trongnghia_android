package com.google.bookticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.bookticket.Controller.TimeAdapter;
import com.google.bookticket.Model.Movie;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChooseTimeActivity extends AppCompatActivity {
    TextView choosetime_movieName,userID_time;
    RecyclerView choosetime_recycleview;
    ImageButton chosetime_btnback;

    public String movieID;
    public String movieName;
    public String userID;
    TimeAdapter timeAdapter;
    List<TimeStart> timeStarts;

    FirebaseFirestore fdb;
    CollectionReference coll_tickets, coll_movies, coll_rooms;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);


        bundle = getIntent().getBundleExtra("movieData");

        if(bundle != null) {
            movieID = bundle.getString("movieID");
        }
        userID=bundle.getString("userID");
        //userID_time.setText(bundle.getString("userID"));
        initID();
        timeStarts = new ArrayList<>();
        timeAdapter = new TimeAdapter(timeStarts, this);

        choosetime_recycleview.setLayoutManager(new LinearLayoutManager(this));
        choosetime_recycleview.setAdapter(timeAdapter);

        fdb = FirebaseFirestore.getInstance();
        coll_tickets = fdb.collection("tickets");
        coll_movies = fdb.collection("Movie");
        coll_rooms = fdb.collection("rooms");


        coll_movies.document(movieID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            Movie movie = doc.toObject(Movie.class);
                            if(movie != null) {
                                choosetime_movieName.setText(movie.getMovieName());
                            }
                        }
                    }
                });
        userID_time.setText(userID);
        coll_rooms.whereEqualTo("movieID", movieID)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                                timeStarts.add(new TimeStart(documentSnapshot.getString("timestart"), documentSnapshot.getString("room"),documentSnapshot.getString("date")));
                            }
                            timeAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ChooseTimeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        chosetime_btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initID() {
        choosetime_movieName = findViewById(R.id.choosetime_movieName);
        choosetime_recycleview = findViewById(R.id.choosetime_recycleview);
        chosetime_btnback = findViewById(R.id.chosetime_btnback);
        userID_time=findViewById(R.id.userID_time);
    }
    public void ChooseSeat(String timestart,String room,String movieID,String movieName,String date){
        Intent intent = new Intent(ChooseTimeActivity.this, ChooseSeatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("date",date);
        bundle.putString("timestart",timestart);
        bundle.putString("room",room);
        bundle.putString("movieID",movieID);
        bundle.putString("movieName",movieName);
        bundle.putString("userID", userID);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

}