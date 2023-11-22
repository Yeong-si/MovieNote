package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.movienote.databinding.ActivityMovieNoteSearchBinding;

public class MovieNoteSearchActivity extends AppCompatActivity {

    ActivityMovieNoteSearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMovieNoteSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}