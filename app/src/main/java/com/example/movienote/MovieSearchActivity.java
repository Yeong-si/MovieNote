package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.movienote.databinding.ActivityMovieSearchBinding;

public class MovieSearchActivity extends AppCompatActivity {
    ActivityMovieSearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMovieSearchBinding.inflate(getLayoutInflater());

        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        String text1=null;

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = binding.searchBar.getText().toString();
                //Movie.main(text);
            }
        });

//        String a = String.valueOf(binding.searchBar.getText());
//
//        Movie.main(a);


    }
}