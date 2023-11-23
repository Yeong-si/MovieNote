package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movienote.databinding.ActivityFinishedMovieBinding;

public class FinishedMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityFinishedMovieBinding binding = ActivityFinishedMovieBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.title.setText("어쩌고"+"님이 본 영화");

        binding.plusmovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinishedMovieActivity.this, MovieSearchActivity.class));
            }
        });


    }
}