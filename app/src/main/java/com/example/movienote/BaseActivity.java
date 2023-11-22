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

        binding.MovieNoteSearchButton.setOnClickListener(this);
        binding.PartySearchButton.setOnClickListener(this);
        binding.PartyInformationButton.setOnClickListener(this);
        binding.PartyButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.MovieNoteSearchButton){
            Intent intent = new Intent(getApplicationContext(), MovieNoteSearchActivity.class);
            startActivity(intent);
        } else if (v == binding.PartySearchButton) {
            Intent intent = new Intent(getApplicationContext(), PartySearchActivity.class);
            startActivity(intent);
        } else if (v == binding.PartyInformationButton) {
            Intent intent = new Intent(getApplicationContext(), PartyInformationActivity.class);
            startActivity(intent);
        } else if (v == binding.PartyButton) {
            Intent intent = new Intent(getApplicationContext(), PartyActivity.class);
            startActivity(intent);
        }
    }
}