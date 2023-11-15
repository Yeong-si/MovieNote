package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movienote.databinding.ActivityBaseBinding;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityBaseBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.MovieSearchButton.setOnClickListener(this);
        binding.PartySearchButton.setOnClickListener(this);
        binding.PartyInformationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.MovieSearchButton){
            Intent intent = new Intent(getApplicationContext(), MovieSearchActivity.class);
            startActivity(intent);
        } else if (v == binding.PartySearchButton) {
            Intent intent = new Intent(getApplicationContext(), PartySearchActivity.class);
            startActivity(intent);
        } else if (v == binding.PartyInformationButton) {
            Intent intent = new Intent(getApplicationContext(), PartyInformationActivity.class);
            startActivity(intent);
        }
    }
}