package com.example.movienote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.movienote.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.finishedMoviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FinishedMovieActivity.class));
            }
        });

        binding.tostratMoviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ToStartMovieActivity.class));
            }
        });
        BottomNavigationView navigationBarView = findViewById(R.id.bottom_navigation);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.page_1) {
                    // Respond to navigation item 1 click
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (itemId == R.id.page_2) {
                    // Respond to navigation item 2 click
                    Intent intent = new Intent(MainActivity.this, PieChartActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (itemId == R.id.page_3) {
                    // Respond to navigation item 3 click
                    Intent intent = new Intent(MainActivity.this, BaseActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (itemId == R.id.page_4) {
                    // Respond to navigation item 4 click
                    Intent intent = new Intent(MainActivity.this, GoogleSignInActivity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });


    }
}