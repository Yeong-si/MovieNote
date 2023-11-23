package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movienote.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        initializeCloudFirestore();

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

//        Thread thread = new Thread(){
//            public void run(){
//                Movie api = new Movie();
//
//            }
//        };

    }
    private void initializeCloudFirestore() {
        db = FirebaseFirestore.getInstance();
    }
}