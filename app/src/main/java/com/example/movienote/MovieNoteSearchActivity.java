// 다른 사람들이 쓴 영화감상노트를 검색해서 볼 수 있는 액티비티

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