package com.google.bookticket;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.bookticket.Controller.MovieAdapter;
import com.google.bookticket.Model.Movie;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment {
    List<Movie> movieList;
    MovieAdapter moviesAdapter;
    RecyclerView frag_moviesRecycleview;
    EditText searchPhim;
    String userID;
    private MainActivity mainActivity;
    private MovieDetailActivity movieDetailActivity;
    FirebaseFirestore fdb;
    CollectionReference collec_movies;
    TextView userID_moive;
    private Button getID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movies, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        fdb = FirebaseFirestore.getInstance();
        collec_movies = fdb.collection("Movie");
        movieList = new ArrayList<>();
        frag_moviesRecycleview = view.findViewById(R.id.frag_moviesRecycleview);
        searchPhim = (EditText) view.findViewById(R.id.searchbar);
        String txtsearchphim = searchPhim.getText().toString();
        String searchmovie=searchPhim.getText().toString().trim();
        moviesAdapter = new MovieAdapter(movieList, this);

        frag_moviesRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        frag_moviesRecycleview.setAdapter(moviesAdapter);


        userID = mainActivity.getUserID();


        if (searchmovie.isEmpty()){
            collec_movies.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                                    Movie movie = documentSnapshot.toObject(Movie.class);
                                    movie.setMovieID(documentSnapshot.getId());
                                    movieList.add(movie);

                                }
                                moviesAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }else
        {
            filter(searchmovie.toString());
        }
        searchPhim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void afterTextChanged(Editable editable) {
                    if(editable.toString() != null) {
                        filter(editable.toString());
                    }else{
                        collec_movies.get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()) {
                                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                                                Movie movie = documentSnapshot.toObject(Movie.class);
                                                movie.setMovieID(documentSnapshot.getId());
                                                movieList.add(movie);

                                            }
                                            moviesAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                    }
            }

        });

    }
    private void filter(String text){
        List<Movie> filterlist = new ArrayList<>();
        for (Movie items : movieList){
            if(items.getMovieName().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(items);
            }
        }
        moviesAdapter.filterlist(filterlist);
    }
    public void Detail(String movieID, String movieName){
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("movieID", movieID);
        bundle.putString("movieName", movieName);
        bundle.putString("userID",userID);
        intent.putExtra("movieData", bundle);
        startActivity(intent);
    }
}