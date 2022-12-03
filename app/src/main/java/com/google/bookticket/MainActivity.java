package com.google.bookticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    MoviesFragment moviesFragment;
    HomeFragment homeFragment;
    ProfileFragment profileFragment;
    BottomNavigationView bottomNavigationView;
    String namemovie = "one piece";
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        moviesFragment = new MoviesFragment();
        homeFragment = new HomeFragment();
        bottomNavigationView = findViewById(R.id.main_navigation_menu);

        Bundle bundle = getIntent().getBundleExtra("userID");

        userID = bundle.getString("userID");
        Toast.makeText(this,"Wellcome",Toast.LENGTH_SHORT).show();
        if (getIntent().getStringExtra("movieName") != null){
            namemovie = getIntent().getStringExtra("movieName");
        }


        Bundle bundle1= new Bundle();
        bundle1.putString("userID",userID);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, moviesFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment= null;
                switch (item.getItemId()){
                    case R.id.menu_movies:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, moviesFragment).commit();
                        break;
                    case R.id.menu_profile:
                        selectedFragment= new ProfileFragment();
                        selectedFragment.setArguments(bundle1);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,selectedFragment).commit();
                        break;
                }
                return true;
            }
        });


    }
    public String getUserID() {
        return userID;
    }

}